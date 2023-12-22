package com.example.memorypuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckOfMemoryCards {
    private ArrayList<MemoryCard> deck;
    private int size;


    public DeckOfMemoryCards() {
        this.deck = new ArrayList<>();
        size = 0;
        List<String> suits = Card.getValidSuits();
        List<String> faceNames = Card.getValidFaceNames();
        for (String suit : suits) {
            for (String faceName : faceNames) {
                if(size < 36) {
                    deck.add(new MemoryCard(suit, faceName));
                    deck.add(new MemoryCard(suit, faceName));
                    size += 2;
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card dealTopCard() {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        } else {
            return null;
        }
    }

    public Card getCard(Card cd) {
        if(deck.contains(cd)) {
            return cd;
        }
        return null;
    }

    public ArrayList<MemoryCard> getCards() {
        return deck;
    }

    public int getNumberOfCards() {
        return  deck.size();
    }
}
