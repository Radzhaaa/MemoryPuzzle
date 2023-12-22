package com.example.memorypuzzle;

import lombok.Getter;

import java.util.UUID;

public class MemoryCard extends Card {
    private String suit;
    private String faceName;
    private Boolean matched;
    @Getter
    private String id;

    public MemoryCard(String suit, String faceName) {
        super(suit, faceName);
        this.suit = suit;
        this.faceName = faceName;
        this.matched = false;
        this.id = UUID.randomUUID().toString();
    }

    public MemoryCard(String suit, String faceName, String id) {
        super(suit, faceName);
        this.suit = suit;
        this.faceName = faceName;
        this.matched = false;
        this.id = id;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public boolean isSameCard(MemoryCard otherCard){
        return((this.getSuit().equals(otherCard.getSuit())) &&
                (this.getFaceName().equals(otherCard.getFaceName())));
    }

    @Override
    public String toString() {
        return suit + "_of_" + faceName + "_" + id;
    }
}
