package com.example.memorypuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckOfCards {
    private ArrayList<Card> deck;

    public DeckOfCards() {
        this.deck = new ArrayList<>();
        List<String> suits = Card.getValidSuits();
        List<String> faceNames = Card.getValidFaceNames();
        for (String suit : suits) {
            for (String faceName : faceNames) {
                deck.add(new Card(suit, faceName));
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

    public ArrayList<Card> getCards() {
        return deck;
    }

    public int getNumberOfCards() {
        return  deck.size();
    }
}
