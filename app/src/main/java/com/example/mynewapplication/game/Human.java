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
    
    @Override
    public Cards playCard() {
        
        ArrayList<Cards> playableCards = checkPlayableCards();
        Cards chosenCard = null;
        String choosenNameCard = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do {
            
            try {
                choosenNameCard = reader.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            
            try {
                chosenCard = Cards.valueOf(choosenNameCard);
                if (!hand.contains(chosenCard)) {
                    throw new IllegalArgumentException();
                } else if (!playableCards.contains(chosenCard)) {
                    throw new Exception();
                }
            } catch (IllegalArgumentException e) {
                System.err.println("*The card [" + choosenNameCard + "] does not exist in your hand, try again");
                System.out.print(" - ");
                chosenCard = null;
            } catch (Exception e) {
                System.err.println("*You cannot play the card [" + choosenNameCard + "]");
                System.out.print(" - ");
                chosenCard = null;
            }
            
        } while (chosenCard == null);

        hand.remove(chosenCard);

        return chosenCard;
    }
}