package com.example.memorypuzzle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;

public class Server {
    private static ServerSocket serverSocket;
    private static HashMap<String, Connection> connections = new HashMap<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(6666);
            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String[] data = bufferedReader.readLine().split(":");
                    // operation:username:roomName
                    if (Objects.equals(data[0], "create_session")) {
                        Player player = new Player(data[1], data[2]);
                        Connection connection = new Connection(bufferedReader, printWriter, player);
                        connections.put(player.getId(), connection);
                        Thread con = new Thread(connection);
                        System.out.println("Комната была создана..");
                        con.start();
                        break;
                    } else if (Objects.equals(data[0], "join_session")) {
                        System.out.println("Попытка подключения...");
                        Player player2 = new Player(data[1]);
                        if (connections.containsKey(data[2])) {
                            connections.get(data[2]).addStreams(bufferedReader, printWriter, player2);
                        } else {
                            printWriter.println("join_session_failed");
                        }
                        break;
                    } else {
                        socket.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
