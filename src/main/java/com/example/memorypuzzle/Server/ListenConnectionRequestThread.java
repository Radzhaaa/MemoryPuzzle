package com.example.memorypuzzle.Server;

import java.io.IOException;
import java.net.Socket;

public class ListenConnectionRequestThread extends Thread {
    private Server server;

    public  ListenConnectionRequestThread(Server server){
        this.server = server;
    }

    @Override
    public void run(){
        while (!this.server.serverSocket.isClosed()){
            try {
                Socket nSoket = this.server.serverSocket.accept();
                Client nClient = new Client(nSoket);
                nClient.Listen();
                server.clients.add(nClient);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
