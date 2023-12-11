package com.example.memorypuzzle.Server;

public class ClientRemovingControlThread extends Thread{
    private Server server;

    public ClientRemovingControlThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {

        while(!this.server.serverSocket.isClosed())
        {
            for(Client client : Server.clients)
            {
                if(client.socket.isClosed())
                {
                    Server.clients.remove(client);
                }
            }
        }
    }
}
