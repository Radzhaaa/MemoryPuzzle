package com.example.memorypuzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServerConnection implements Runnable {
    private boolean gameStarted = false;
    private boolean gameInitialized = false;
    private boolean moveChanged = false;
    private int countOfPlayers = 0;
    private int currentPlayer = 0;
    private String id1 = "";
    private String id2 = "";
    private MemoryCard cd1;
    private MemoryCard cd2;
    private DeckOfMemoryCards deck;
    private ArrayList<String> matchedCards;
    private ArrayList<PrintWriter> writers = new ArrayList<>();
    private ArrayList<BufferedReader> readers = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    public ServerConnection(BufferedReader in, PrintWriter out, Player h) {
        this.readers.add(in);
        this.writers.add(out);
        this.players.add(h);
        this.countOfPlayers += 1;
        this.sendDataToAll("waiting");
    }

    public void addStreams(BufferedReader in, PrintWriter out, Player p) {
        if (this.countOfPlayers < 2) {
            this.readers.add(in);
            this.writers.add(out);
            sendData("join_session_success", out);
            this.players.add(p);
            this.countOfPlayers += 1;
            sendDataToAll("started");
            gameStarted = true;
        } else {
            sendData("join_session_failed", out);
        }
    }

    @Override
    public void run() {
        while (true) {
            while (gameStarted) {
                if (!gameInitialized) {
                    startGame();
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

    private void parseData(String data) {
        // flip_card:id_карты
        String[] params = data.split(":");
        String oper = params[0];
        if (Objects.equals(oper, "flip_card")) {
            String id = params[1];
            boolean flag = false;
            if (!id1.isEmpty()) {
                if (id2.isEmpty()) {
                    id2 = id;
                    MemoryCard card1 = getCardById(id1);
                    MemoryCard card2 = getCardById(id2);
                    if (card1.match(card2)) {
                        matchedCards.add(id1);
                        players.get(currentPlayer).increaseCountMatches();
                        players.get(currentPlayer).increaseCountGuesses();
                        matchedCards.add(id2);
                        flag = true;
                        moveChanged = false;
                        getPlayerByMove().increaseCountMatches();
                    } else {
                        flag = true;
                        players.get(currentPlayer).increaseCountGuesses();
                        moveChanged = true;
                    }
                }
            } else {
                id1 = id;
                moveChanged = false;
                flag = false;
            }
            sendGameData(id1, id2);
            if(flag) {
                id1 = "";
                id2 = "";
            }
        }
    }

    private MemoryCard getCardById(String id) {
        for(MemoryCard cd: deck.getCards()) {
            if(Objects.equals(cd.getId(), id)) {
                return cd;
            }
        }
        return null;
    }

    private Player getPlayerByMove() {
        return players.get(currentPlayer);
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
        if(list.isEmpty()) {
            return "null";
        }
        return list.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    private void sendGameData(String ID1, String ID2) {
        // game:открытые_карты:конец_игры,true/false(в случае победил или нет):число угаданных этого игрока, число угаданных другого:число открываний, число открываний другого:разрешение
        changeMove();
        for (int i = 0; i < countOfPlayers; i++) {
            String data = "game:";
//            data += id + ":";
            if(matchedCards.isEmpty()) {
                if(ID2.isEmpty()) {
                    data += ID1 + ":";
                }
                else {
                    data += ID1 + "," + ID2 + ":";
                }

            }
            else {
                data += matchedCards.stream().map(Object::toString).collect(Collectors.joining(",")) + ",";
                if(ID2.isEmpty()) {
                    data += ID1 + ":";
                }
                else {
                    data += ID1 + "," + ID2 + ":";
                }
            }
            data += getEndData() + ":";
            data += getPlayersStat(i) + ":";
            data += getPermissionById(i);
            sendData(data, writers.get(i));
        }
    }

    private String getPlayersStat(int i) {
        String data = "";
        if(i == 0) {
            data += players.get(0).getCount_matches() + "," + players.get(1).getCount_matches() + ",";
            data += players.get(0).getCount_guesses() + "," + players.get(1).getCount_guesses();
            return data;
        }
        else {
            data += players.get(1).getCount_matches() + "," + players.get(0).getCount_matches() + ",";
            data += players.get(1).getCount_guesses() + "," + players.get(0).getCount_guesses();
            return data;
        }
    }

    private boolean checkEnd() {
        return matchedCards.size() == deck.getCards().size();
    }

    private String getEndData() {
        return checkEnd() + "," + getLeader();
    }

    private Player getLeader() {
        int max = 0;
        Player leader = null;
        for(Player p: players) {
            if(p.getCount_matches() > max) {
                leader = p;
            }
        }
        return leader;
    }

    private void startGame() {
        deck = new DeckOfMemoryCards();
        deck.shuffle();
        matchedCards = new ArrayList<>();
        for (int i = 0; i < writers.size(); i++) {
            // start_game:card1,...:perm:other player nick
            String data = "start_game:";
            data += getStringOfCards(deck.getCards()) + ":";
            data += getPermissionById(i) + ":";
            data += i == 0 ? players.get(1).getNickname() : players.get(0).getNickname();
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
