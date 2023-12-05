package com.example.memorypuzzle;

import java.util.Arrays;
import java.util.List;

public class Card {
    private String suit;
    private String faceName;

    public Card(String suit, String faceName) {
        setSuit(suit);
        setFaceName(faceName);
    }

    public static List<String> getValidSuits() {
        return Arrays.asList("hearts", "diamonds", "clubs", "spades");
    }

    public static List<String> getValidFaceNames() {
        return Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace");
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        suit = suit.toLowerCase();
        if (getValidSuits().contains(suit)) {
            this.suit = suit;
        } else {
            throw new IllegalArgumentException(suit + "invalid, must be one of " + getValidSuits());
        }
    }

    public String getFaceName() {
        return faceName;
    }

    public void setFaceName(String faceName) {
        faceName = faceName.toLowerCase();
        if (getValidFaceNames().contains(faceName)) {
            this.faceName = faceName;
        } else {
            throw new IllegalArgumentException(faceName + "invalid, must be one of " + getValidFaceNames());
        }
    }

    public String toString() {
        return faceName + "of" + suit;
    }

    public String getColour() {
        if (suit.equals("hearts") || suit.equals("diamonds")) {
            return "red";
        } else {
            return "black";
        }
    }

    public int getValue() {
        return getValidFaceNames().indexOf(faceName) + 2;
    }
}
