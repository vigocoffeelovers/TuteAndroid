package com.example.mynewapplication.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Human extends Player {

    public Human(String name) {
        super(name);
    }

    public Human(String name, Game game) {
        super(name, game);
    }

    /**
     * Human interactions will be handled on the UI not here, the function will return null in order to stop the execution.
     * When the user touches a card it will turn on an event which will continue with the execution
     * @return null
     */
    @Override
    public Cards playCard() {
        return null;
    }
}