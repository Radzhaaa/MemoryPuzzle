package com.example.memorypuzzle;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class Card {
    private String suit;
    @Getter
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

    public void setSuit(String suit) {
        suit = suit.toLowerCase();
        if (getValidSuits().contains(suit)) {
            this.suit = suit;
        } else {
            throw new IllegalArgumentException(suit + "invalid, must be one of " + getValidSuits());
        }
    }

    public void setFaceName(String faceName) {
        faceName = faceName.toLowerCase();
        if (getValidFaceNames().contains(faceName)) {
            this.faceName = faceName;
        } else {
            throw new IllegalArgumentException(faceName + "invalid, must be one of " + getValidFaceNames());
        }
    }

    public Image getImage() {
        String pathName = "images/" + faceName + "_of_" + suit + ".png";
        return new Image(Card.class.getResourceAsStream(pathName));
    }

    public Image getBackOfCardImage(){
        return new Image(Card.class.getResourceAsStream("images/back_of_card.png"));
    }

    public String toString() {
        return suit + "_of_" + faceName;
    }

}
