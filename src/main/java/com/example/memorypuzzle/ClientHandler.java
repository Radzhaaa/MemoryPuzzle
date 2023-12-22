package com.example.memorypuzzle;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler {
    public Socket socket;
    private BufferedReader in;
    public PrintWriter out;
    public String username;
    public boolean isTurn;
    private WaitController wc;
    private GameController gc;
    private MenuController mc;
    private EndController ec;
    private ArrayList<MemoryCard> table;
    private ArrayList<MemoryCard> opened;
    private int count_guess = 0;
    private int count_match = 0;

    public ClientHandler() {
        isTurn = false;
        try {
            socket = new Socket(InetAddress.getLocalHost(), 6666);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this::receive, "client receive thread").start();
    }


    private void receive() {
        while (true) {
            String data = readData();
            System.out.println(data);
            Platform.runLater(
                    () -> parseData(data)
            );
        }
    }

    public void createSession(String name, String s_id) throws NumberFormatException {
        this.username = name;
        String data = "create_session" + ":" + name + ":" + s_id;
        sendData(data);
        System.out.println(s_id);
    }

    public void joinSession(String username, String session_id) throws ClassCastException {
        this.username = username;
        String data = "join_session:" + this.username + ":" + session_id;
        sendData(data);
    }

    private void sendData(String data) {
        this.out.println(data);
        this.out.flush();
    }

    private String readData() {
        try {
            return this.in.readLine();
        } catch (IOException ex) {
            System.out.println("Error Occurred in readData in ClientHandler: " + ex.toString());
        }
        return null;
    }

    private void parseData(String data) {
        String[] params = data.split(":");
        String command = params[0];
        switch (command) {
            case "waiting", "join_session_success": {
                openWait();
                break;
            }
            case "started": {
                openGame();
                break;
            }
            case "join_session_failed": {
                System.out.println("Невозможно войти в комнату");
                Platform.exit();
                closeStreams();
                break;
            }
            case "start_game": {
                // start_game:card1,...:perm:other player nick
                String[] cards = params[1].split(",");
                table = new ArrayList<>();
                for(String str: cards) {
                    String[] card_info = str.split("_");
                    table.add(new MemoryCard(card_info[0], card_info[2], card_info[3]));
                }
                gc.setTable(table);
//                setTableFromData(cards);
                String other_player = params[3];
                gc.setPlayerName1(this.username);
                gc.setPlayerName2(other_player);
                gc.setImages();
                isTurn = Boolean.parseBoolean(params[2]);
                gc.setDisableActions(isTurn);
                break;
            }
            case "game": {
                // game:открытые_карты:конец_игры,true/false(в случае победил или нет):число угаданных этого игрока, число угаданных другого,число открываний, число открываний другого:разрешение
                String[] cards = params[1].split(",");
                setOpenedFromData(cards);
            }
            String[] end = params[2].split(",");
            boolean endGame = Boolean.parseBoolean(end[0]);
            if (endGame) {
                openEnd();
                boolean iWin = Boolean.parseBoolean(end[1]);
                if (iWin) {
                    String ending = "Вы победили";
                    ec.setTextState(ending);
                } else {
                    String ending = "Вы проиграли";
                    ec.setTextState(ending);
                }
                closeStreams();
            }
            String[] stats = params[3].split(",");
            gc.setLabels(stats[0], stats[1], stats[2], stats[3]);
            isTurn = Boolean.parseBoolean(params[4]);
            gc.setImages();
            gc.setDisableActions(isTurn);
            break;
        }
    }

    private void setTableFromData(String[] data) {
        table = setCardsFromData(data);
        gc.setTable(table);
    }

    private ArrayList<MemoryCard> setCardsFromData(String[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i].strip();
        }
        ArrayList<MemoryCard> cards = new ArrayList<>();
        for (String str : data) {
            cards.add(getCardById(str));
        }
        return cards;
    }

    private MemoryCard getCardById(String id) {
        for (MemoryCard cd : table) {
            if (Objects.equals(cd.getId(), id)) {
                return cd;
            }
        }
        return null;
    }

    private void setOpenedFromData(String[] data) {
        opened = setCardsFromData(data);
        gc.setOpenedCards(opened);
    }


    private void openWait() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Wait.fxml"));
            Parent root = loader.load();

            WaitController wc = loader.getController();
            setWaitController(wc);

            Main.stage.close();
            Main.stage.setTitle("WAITING ROOM");
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error in loading wait");
        }
    }

    public void closeStreams() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openGame() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent root = loader.load();
            GameController gc = loader.getController();
            setGameController(gc);

            Main.stage.close();
            Main.stage.setTitle("Memory Card");
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error in loading table");
        }
    }

    private void openEnd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end.fxml"));
            Parent root = loader.load();
            EndController ec = loader.getController();
            setEndController(ec);

            Main.stage.close();
            Main.stage.setTitle("ENDING");
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error in loading table");
        }
    }

    public void sendGameMove(String operation, String id) {
        sendData(operation + ":" + id);
    }

    public void setGameController(GameController tc) {
        this.gc = tc;
    }

    public void setWaitController(WaitController wc) {
        this.wc = wc;
    }

    public void setMenuController(MenuController mc) {
        this.mc = mc;
    }

    public void setEndController(EndController ec) {
        this.ec = ec;
    }
}
