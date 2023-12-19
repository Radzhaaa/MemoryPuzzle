package com.example.memorypuzzle.server;

import javafx.fxml.FXML;

import java.awt.*;
import java.util.List;

public class Room {
    @FXML
    TextField roomName;
    protected String uniqueCode;
    private List<Connection> connections;
    private int countOfConnections = 2;

    public Room(String uniqueCode, List<Connection> connections) {
        this.uniqueCode = roomName.getText();
        this.connections = connections;
    }

    public  synchronized void addConnection(Connection connection){
        if(connections.size() < 2){
            connections.add(connection);
        }
        else{
            System.out.println("The room is full");
        }
    }

    public synchronized void removeConnection(Connection connection){
        connections.remove(connection);
    }
}
