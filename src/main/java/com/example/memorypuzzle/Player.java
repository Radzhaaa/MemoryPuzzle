package com.example.memorypuzzle;

public class Player {
    private  String username;
    private byte placeId;
    private int numOfGuesses;
    private int numOfMatches;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte getPlaceId() {
        return placeId;
    }

    public void setPlaceId(byte placeId) {
        this.placeId = placeId;
    }

    public int getNumOfGuesses() {
        return numOfGuesses;
    }

    public void setNumOfGuesses(int numOfGuesses) {
        this.numOfGuesses = numOfGuesses;
    }

    public int getNumOfMatches() {
        return numOfMatches;
    }

    public void setNumOfMatches(int numOfMatches) {
        this.numOfMatches = numOfMatches;
    }

    public Player(String username, byte placeId, int numOfGuesses, int numOfMatches) {
        this.username = username;
        this.placeId = placeId;
        this.numOfGuesses = numOfGuesses;
        this.numOfMatches = numOfMatches;
    }
}
