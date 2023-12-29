package com.example.memorypuzzle;

import lombok.Getter;
import lombok.Setter;


public class MemoryCard extends Card {
    @Getter
    private String suit;
    private String faceName;
    @Setter
    @Getter
    private Boolean matched;

    public MemoryCard(String suit, String faceName) {
        super(suit, faceName);
        this.suit = suit;
        this.faceName = faceName;
        this.matched = false;
    }


    @Override
    public String toString() {
        return suit + "_of_" + faceName;
    }

    public boolean isSameCard(MemoryCard otherCard){
        return((this.getSuit().equals(otherCard.getSuit())) &&
                (this.getFaceName().equals(otherCard.getFaceName())));
    }
}
