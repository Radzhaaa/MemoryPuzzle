package com.example.memorypuzzle.Server;

public class ClientPairingThread extends Thread{
    Client client;
    public  ClientPairingThread(Client client){
        this.client = client;
    }

    @Override
    public void run(){
        while(this.client.socket.isConnected() && this.client.isWantToPair == true && this.client.isPaired == false){
            try {
                Server.pairingLockForTwoPair.acquire(1);
                Client chosenPair = null;
                while (this.client.socket.isConnected() && chosenPair == null){
                    for(Client client : Server.clients) {
                        if (client != this.client && client.isPaired == false && client.isWantToPair == true) {
                            chosenPair = client;
                            this.client.pair = client;
                            client.pair = this.client;
                            this.client.isWantToPair = false;
                            this.client.isPaired = true;
                            client.isWantToPair = false;
                            client.isPaired = true;
                        }
                    }
                    sleep(1000);
                }
                Server.pairingLockForTwoPair.release(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
