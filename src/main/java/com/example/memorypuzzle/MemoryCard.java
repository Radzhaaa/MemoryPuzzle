package com.example.memorypuzzle;

public class MemoryCard extends Card {
    private Boolean matched;

    public MemoryCard(String suit, String faceName) {
        super(suit, faceName);
        this.matched = false;
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
}
