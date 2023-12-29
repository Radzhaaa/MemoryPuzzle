package com.example.memorypuzzle;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Player {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String id;
    @Getter
    private int countOfGuesses;
    @Getter
    private int countOfMatches;

    public Player(String username) {
        this.username = username;
        id = UUID.randomUUID().toString();
        countOfMatches = 0;
        countOfGuesses = 0;
    }

    public Player(String username, String id) {
        this.username = username;
        this.id = id;
        countOfMatches = 0;
        countOfGuesses = 0;
    }

    public void increaseCountGuesses() {
        countOfGuesses++;
    }

    public void increaseCountMatches() {
        countOfMatches++;
    }
}
