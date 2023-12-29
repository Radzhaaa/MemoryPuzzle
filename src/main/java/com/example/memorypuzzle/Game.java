package com.example.memorypuzzle;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Game {
    @Getter
    private static DeckOfMemoryCards deck;
    static boolean matched = false;
    @Getter
    @Setter
    static MemoryCard card1 = null;
    @Getter
    @Setter
    static MemoryCard card2 = null;

    public static boolean isMatched() {
        return matched;
    }


    public static void startGame() {
        deck = new DeckOfMemoryCards();
        deck.shuffle();

    }

    public static boolean end() {
        for (MemoryCard memoryCard : deck.getCards()) {
            if (memoryCard.getMatched() == false) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Integer> getOpenedCards() {
        ArrayList<Integer>  opened = new ArrayList<>();
        for(MemoryCard card: deck.getCards()) {
            if(card.getMatched()) {
                opened.add(deck.getCards().indexOf(card));
            }
        }

//        ArrayList<MemoryCard> opened = new ArrayList<>();
//        for (MemoryCard memoryCard : deck.getCards()) {
//            if (memoryCard.getMatched()) {
//                opened.add(memoryCard);
//            }
//        }
        return opened;
    }

}
