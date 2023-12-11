package com.example.memorypuzzle.PClient;

import com.example.memorypuzzle.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PClient {
    private final Game game;
    public Socket socket;
    public ObjectInputStream sInput;
    public ObjectOutputStream sOutput;
    public boolean isPaired = false;
    public String serverIP;
    public int serverPort;
    public PClientListenThread listenThread;

    public PClient(Game game) {
        this.game = game;
    }

    public  void Connect(String serverIP,int serverPort){
        try {
            System.out.println("Connecting to the server");
            this.serverIP = serverIP;
            this.serverPort = serverPort;
            this.socket = new Socket(this.serverIP,this.serverPort);
            System.out.println("Connecting to the server");
            sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            sInput = new ObjectInputStream(this.socket.getInputStream());
            listenThread = new PClientListenThread(this);
            this.listenThread.start();
        } catch (IOException ex) {
            System.out.println("Can not connected to the server.");
        }
    }

    public void Stop(){
        if (this.socket != null) {

            try {
                this.socket.close();
                this.sOutput.flush();
                this.sOutput.close();
                this.sInput.close();
            } catch (IOException ex) {
                Logger.getLogger(PClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Send(Object message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(PClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
