package com.example.memorypuzzle.Server;

import com.example.memorypuzzle.Message;

public class ClientListenThread extends Thread{
    Client client;

    public  ClientListenThread(Client client){
        this.client = client;
    }

    @Override
    public void run(){
        while(!this.client.socket.isClosed()){
            try {
                Message msg = (Message) (this.client.cInput.readObject());
                switch (msg.type) {
                    case PAIRING:
                        this.client.isWantToPair = true;
                        this.client.pairingThread.start();
                        break;
                    case LEAVE:
                        this.client.isPaired = false;
                        this.client.isWantToPair = false;
                        this.client.pair.isWantToPair = false;
                        this.client.pair.isPaired = false;
                        this.client.pair.pair = null;
                        this.client.pair = null;

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
