package com.example.mynewapplication.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Game {

    private Table table;

    public Game(ArrayList<Player> players) {
        table = new Table(players);
    }

    
    public void start() {
        table.startGame();
    }

    
    public static void main(String[] args) {

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(
                new Human("Sergio"),
                new Player("Marcos"),
                new Player("Roi"),
                new Player("Pablo")
        ));

        Game game = new Game(players);
        game.start();

    }

    public Table getTable() {
        return table;
    }
}



