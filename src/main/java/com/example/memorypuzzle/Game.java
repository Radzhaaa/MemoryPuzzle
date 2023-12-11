package com.example.memorypuzzle;


import com.example.memorypuzzle.PClient.PClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private PClient pClient;
    private List<Integer> cards;

    public Game() {
        this.pClient = new PClient(this);
        this.pClient.Connect("127.0.0.1", 4000);
        if (this.pClient.socket == null) {
            System.exit(0);
        }
        cards = new ArrayList<>();
    }

    public void initializeGame(int numPairs) {
        for (int i = 0; i < numPairs; i++) {
            cards.add(i);
            cards.add(i);
        }
        Collections.shuffle(cards);
    }

    public int getCard(int index) {
        return cards.get(index);
    }

    public boolean isMatch(int card1, int card2) {
        return card1 == card2;
    }

    public int getNumCards() {
        return cards.size();
    }
}

