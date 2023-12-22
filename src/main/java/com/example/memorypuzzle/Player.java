package com.example.memorypuzzle;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Player {
    @Getter
    @Setter
    private String nickname;
    @Getter
    @Setter
    private String id;
    @Getter
    private int count_guesses;
    @Getter
    private int count_matches;

    public Player(String nickname) {
        this.nickname = nickname;
        id = UUID.randomUUID().toString();
        count_guesses = 0;
        count_guesses = 0;
    }

    public Player(String nickname, String id) {
        this.nickname = nickname;
        this.id = id;
        count_guesses = 0;
        count_guesses = 0;
    }

    public void increaseCountGuesses() {
        count_guesses++;
    }

    public void increaseCountMatches() {
        count_matches++;
    }
}
