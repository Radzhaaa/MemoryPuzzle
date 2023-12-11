package com.example.memorypuzzle.PClient;


import com.example.memorypuzzle.Message;
import com.example.memorypuzzle.PClient.PClient;

import java.io.IOException;

public class PClientListenThread extends Thread {
    PClient pClient;
    public  PClientListenThread(PClient pClient){
        this.pClient = pClient;
    }

    @Override
    public void run(){
        while(!this.pClient.socket.isClosed()){
            try {
                Message msg = (Message) (this.pClient.sInput.readObject());
                switch (msg.type) {
                    case START:
                        break;
                    case PAIRING:
                        this.pClient.isPaired = true;
                        break;
                    case LEAVE:
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
