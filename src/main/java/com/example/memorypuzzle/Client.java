package com.example.memorypuzzle;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    public Socket socket;
    private BufferedReader bufferedReader;
    public PrintWriter printWriter;
    public String username;
    public String roomName;
    public boolean isTurn;
    private boolean gameOver = false;
    @Setter
    private GameController gameController;
    @Setter
    private EndController endController;
    private WaitController waitController;
    private ArrayList<MemoryCard> desk;
    private ArrayList<MemoryCard> opened;
    private com.example.memorypuzzle.MenuController MenuController;

    public Client() {
        isTurn = false;
        try {
            socket = new Socket("10.17.76.105", 6666);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
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
            if(gameOver) {
                break;
            }
        }
    }

    public void createSession(String name, String room) throws NumberFormatException {
        this.username = name;
        String data = "create_session" + ":" + name + ":" + room;
        sendData(data);
        roomName = room;
    }

    public void joinSession(String username, String session_id) throws ClassCastException {
        this.username = username;
        String data = "join_session:" + this.username + ":" + session_id;
        sendData(data);
    }

    private void sendData(String data) {
        this.printWriter.println(data);
    }

    private String readData() {
        try {
            return this.bufferedReader.readLine();
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
                desk = new ArrayList<>();
                for(String str: cards) {
                    String[] card_info = str.split("_of_");
                    desk.add(new MemoryCard(card_info[0], card_info[1]));
                }
                gameController.setDeck(desk);
                System.out.println(desk);
//                setDeskFromData(cards);
                String other_player = params[3];
                gameController.setPlayerName1(this.username);
                gameController.setPlayerName2(other_player);
                gameController.setImages();
                isTurn = Boolean.parseBoolean(params[2]);
                gameController.setDisableActions(isTurn);
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
                gameOver = true;
                openEnd();
                String winner = end[1];
                if (winner.equals(this.username)) {
                    String ending = "Вы победили";
                    endController.setTextState(ending);
                } else {
                    String ending = "Вы проиграли";
                    endController.setTextState(ending);
                }
                closeStreams();
                break;
            }
            String[] stats = params[3].split(",");
            gameController.setLabels(stats[0], stats[1], stats[2], stats[3]);
            isTurn = Boolean.parseBoolean(params[4]);
            gameController.setImages();
            gameController.setDisableActions(isTurn);
            break;
        }
    }

    private void setOpenedFromData(String[] data) {
        opened = setCardsFromData(data);
        gameController.setOpenedCards(opened);
    }

    private ArrayList<MemoryCard> setCardsFromData(String[] data) {

        ArrayList<MemoryCard> cards = new ArrayList<>();
        for (String index : data) {
            MemoryCard c = desk.get(Integer.parseInt(index));
//            String[] cardName = index.split("_of_");
//            cards.add(new MemoryCard(cardName[0],cardName[1]));
            cards.add(c);
        }
        return cards;
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
    private void openWait() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Wait.fxml"));
            Parent root = loader.load();

            WaitController waitController = loader.getController();
            setWaitController(waitController);
            waitController.setRoomNameLabel(roomName);

            Main.stage.close();
            Main.stage.setTitle("WAITING ROOM");
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error in loading wait");
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


    public void sendGameMove(String operation, String index) {
        sendData(operation + ":" + index);
//        sendData(operation + ":" + card);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setEndController(EndController endController) {
        this.endController = endController;
    }

    public void setMenuController(MenuController menuController) {
        this.MenuController = menuController;
    }
    public void setWaitController(WaitController waitController) {
        this.waitController = waitController;
    }

    public void closeStreams() {
        try {
            bufferedReader.close();
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
