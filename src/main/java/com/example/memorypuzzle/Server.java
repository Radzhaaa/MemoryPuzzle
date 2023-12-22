package com.example.memorypuzzle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;

public class Server {
    private static ServerSocket serverSocket;
    private static HashMap<String, ServerConnection> connections = new HashMap<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(6666);
            while(true) {
                Socket socket = serverSocket.accept();
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String[] data = in.readLine().split(":");
                    String operation = data[0];
                    if (Objects.equals(operation, "create_session")) {
                        String nickname = data[1];
                        String session_id = data[2];
                        Player host = new Player(nickname, session_id);
                        ServerConnection connection = new ServerConnection(in, out, host);
                        connections.put(host.getId(), connection);
                        Thread con = new Thread(connection);
                        System.out.println("Комната была создана..");
                        con.setDaemon(true);
                        con.start();
                        break;
                    }
                    else if (Objects.equals(operation, "join_session")) {
                        System.out.println("Попытка подключения...");
                        String nickname = data[1];
                        String session_id = data[2];
                        Player member = new Player(nickname);
                        if (connections.containsKey(session_id)) {
                            connections.get(session_id).addStreams(in, out, member);
                        }
                        else {
                            out.println("join_session_failed");
                        }
                        break;
                    }
                    else {
                        socket.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
