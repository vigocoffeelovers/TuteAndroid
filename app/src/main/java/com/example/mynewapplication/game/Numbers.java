package com.example.mynewapplication.game;

public enum Numbers {
    TWO(0),
    FOUR(0),
    FIVE(0),
    SIX(0),
    SEVEN(0),
    JACK(2),
    HORSE(3),
    KING(4),
    THREE(10),
    ACE(11);

    private Numbers(int value) {
        this.value = value;
    }

    public int getPoints() {
        return value;
    }

    private int value;
}
