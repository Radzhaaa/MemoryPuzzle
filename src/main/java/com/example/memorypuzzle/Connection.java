package com.example.memorypuzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Connection implements Runnable {
    private boolean gameStarted = false;
    private boolean gameInitialized = false;
    private boolean moveChanged = false;
    private int countOfPlayers = 0;
    private int currentPlayer = 0;
    public ArrayList<PrintWriter> writers = new ArrayList<>();
    private ArrayList<BufferedReader> readers = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    public Connection(BufferedReader bufferedReader, PrintWriter printWriter, Player player) {
        this.readers.add(bufferedReader);
        this.writers.add(printWriter);
        this.players.add(player);
        this.countOfPlayers += 1;
        this.sendDataToAll("waiting");
    }

    public void addStreams(BufferedReader bufferedReader, PrintWriter printWriter, Player player) {
        if (this.countOfPlayers < 2) {
            this.readers.add(bufferedReader);
            this.writers.add(printWriter);
            sendData("join_session_success", printWriter);
            this.players.add(player);
            this.countOfPlayers += 1;
            sendDataToAll("started");
            gameStarted = true;
        } else {
            sendData("join_session_failed", printWriter);
        }
    }

    @Override
    public void run() {
        while (true) {
            while (gameStarted) {
                if (!gameInitialized) {
                    Game();
                    gameInitialized = true;
                }
                String data = readData(currentPlayer);
                System.out.println(data);
                parseData(data);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

//
//
//
//
//

    private void parseData(String data) {
        System.out.println(data);
        String[] params = data.split(":");
        String oper = params[0];
        if (Objects.equals(oper, "flip_card")) {
            String cardName = params[1];
//            String[] name = cardName.split("_of_");
//            MemoryCard card = new MemoryCard(name[0],name[1]);
            MemoryCard card = Game.getDeck().getCard(Integer.parseInt(cardName));
            boolean flag = false;
            if(Game.getCard1() != null & Game.getCard2() == null){
                Game.setCard2(card);
                if(Game.getCard1().isSameCard(Game.getCard2())){
                    Game.getCard1().setMatched(true);
                    Game.getCard2().setMatched(true);
                    players.get(currentPlayer).increaseCountMatches();
                        players.get(currentPlayer).increaseCountGuesses();
                        flag = true;
                        moveChanged = false;
                }else{
                    flag = true;
                    players.get(currentPlayer).increaseCountGuesses();
                    moveChanged = true;
                }
            }else{
                Game.setCard1(card);
                moveChanged = false;
                flag = false;
            }
            sendGameData(Game.getDeck().getCards().indexOf(Game.getCard1()), Game.getDeck().getCards().indexOf(Game.getCard2()));
            if (flag) {
                Game.setCard1(null);
                Game.setCard2(null);
            }
        }
//            if (index1 != 0) {
//                if (index2 == 0) {
//                    index2 = Integer.parseInt(index);
//                    System.out.println(index);
//                    Game.flipCard(index1);
//                    Game.flipCard(index2);
////                    MemoryCard card1 = deck.getCard(index1);
////                    MemoryCard card2 = deck.getCard(index2);
//                    if (Game.isMatched()) {
//                        players.get(currentPlayer).increaseCountMatches();
//                        players.get(currentPlayer).increaseCountGuesses();
//                        flag = true;
//                        moveChanged = false;
//                    } else {
//                        flag = true;
//                        players.get(currentPlayer).increaseCountGuesses();
//                        moveChanged = true;
//                    }
//                }
//            } else {
//                index1 = Integer.parseInt(index);
//                moveChanged = false;
//                flag = false;
//            }
//            sendGameData(index1, index2);
//            if (flag) {
//                index1 = 0;
//                index2 = 0;
//            }

//        }
    }

    private void sendGameData(int card1, int card2) {
        // game:открытые_карты:конец_игры,true/false(в случае победил или нет):число угаданных этого игрока, число угаданных другого:число открываний, число открываний другого:разрешение
        changeMove();
        for (int i = 0; i < countOfPlayers; i++) {
            String data = "game:";
//            data += id + ":";
            ArrayList<Integer> opened = Game.getOpenedCards();
            if (!opened.isEmpty()) {
                for(int j = 0; j < opened.size(); j++) {
                    data += opened.get(j) + ",";
                }
            }
            if (card2 == -1) {
                data += card1 + ":";
            } else {
                data += card1 + "," + card2 + ":";
            }
            data += getEndData() + ":";
            data += getPlayersStat(i) + ":";
            data += getPermissionById(i);
            sendData(data, writers.get(i));
        }
        if (checkEnd()) {
            try {
                closeStreams();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void changeMove() {
        if (moveChanged) {
            if (this.currentPlayer == 1) {
                this.currentPlayer = 0;
            } else {
                this.currentPlayer += 1;
            }
            moveChanged = false;
            System.out.println("ход был изменен");
        }
    }

    private String getStringOfCards(List<MemoryCard> list) {
        if (list.isEmpty()) {
            return "null";
        }
        return list.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    private void closeStreams() throws IOException {
        for (int i = 0; i < countOfPlayers; i++) {
            writers.get(i).close();
            readers.get(i).close();
        }
    }

    private String getPlayersStat(int i) {
        String data = "";
        if (i == 0) {
            data += players.get(0).getCountOfMatches() + "," + players.get(1).getCountOfMatches() + ",";
            data += players.get(0).getCountOfGuesses() + "," + players.get(1).getCountOfGuesses();
            return data;
        } else {
            data += players.get(1).getCountOfMatches() + "," + players.get(0).getCountOfMatches() + ",";
            data += players.get(1).getCountOfGuesses() + "," + players.get(0).getCountOfGuesses();
            return data;
        }
    }

    private boolean checkEnd() {
        return Game.end();
    }

    private String getEndData() {
        if (getLeader() != null) {
            return checkEnd() + "," + players.get(currentPlayer).getUsername();
        }
        return checkEnd() + ",null";
    }

    private Player getLeader() {
        int max = 0;
        Player leader = null;
        for (Player p : players) {
            if (p.getCountOfMatches() > max) {
                leader = p;
                max = p.getCountOfMatches();
            }
        }
        return leader;
    }

    private void Game() {
        Game.startGame();
        for (int i = 0; i < writers.size(); i++) {
            // start_game:card1,...:perm:other player nick
            String data = "start_game:";
            data += getStringOfCards(Game.getDeck().getCards()) + ":";
            data += getPermissionById(i) + ":";
            data += i == 0 ? players.get(1).getUsername() : players.get(0).getUsername();
            sendData(data, writers.get(i));
        }
    }

    private boolean getPermissionById(int i) {
        return i == currentPlayer;
    }

    private String readData(int i) {
        try {
            return readers.get(i).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendData(String data, PrintWriter writer) {
        writer.println(data);
    }

    private void sendDataToAll(String data) {
        for (PrintWriter out : writers) {
            out.println(data);
        }
    }
}
