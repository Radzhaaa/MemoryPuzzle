package com.example.memorypuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckOfMemoryCards {
    private ArrayList<MemoryCard> deck;


    public DeckOfMemoryCards() {
        this.deck = new ArrayList<>();
        int size = 0;
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
        shuffle();
    }


    public void shuffle() {
        Collections.shuffle(deck);
    }


    public ArrayList<MemoryCard> getCards() {
        return deck;
    }

    public MemoryCard getCard(int index){
        return getCards().get(index);
    }

//    public int getIndex(){
//        int index = 0;
//        for(Card card: deck){
//
//        }
//    }

}
