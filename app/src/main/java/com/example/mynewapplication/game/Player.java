package com.example.mynewapplication.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class creates a Tute player.
 * @author VigoCoffeeLovers
 */
public class Player {
    
    protected String name;
    protected ArrayList<Cards> hand;
    protected int points;
    protected ArrayList<Suits> sings;
    
    
    /**
     * Creates a player ready to play a Tute match.
     * @param name Name of the player. It will work as an identifier for him. //TODO create a number id?
     */
    public Player(String name) {
        this.hand   = new ArrayList<>();
        this.sings  = new ArrayList<>();
        this.name   = name;
    }
    
    
    /**
     * Add the given card to the player hand.
     * @param card Card to add to the hand
     */
    public void receiveCard(Cards card) {
        hand.add(card);
    }
    
    
    /**
     * Choose a card from among his hand to play in his next play.
     * @param game Game where the player is playing
     * @return the chosen card to play
     */
    public Cards playCard(Table game) {
        ArrayList<Cards> playableCards = checkPlayableCards(game);
        Cards chosen_card = playableCards.get((int)(Math.random()*playableCards.size())); //TODO Now is being choosing a random card
        hand.remove(chosen_card);
        return chosen_card;
    }
    
    
    /**
     * Choose (if exists) a pair of singing cards to sing them in the game. Choose the best 
     * pair among all posibilities to sing.
     * (TUTE > 40s > 20s)
     * 
     * In the case that the chosen one was a "Tute sing", the method will return 4 cards (4 Horses or 4 Kings).
     * 
     * @param game Game where the player is playing
     * @return the list of cards to sing
     */
    public ArrayList<Cards> sing(Table game) {
        
        ArrayList< ArrayList<Cards> > singCards = checkSingableCards(game);
        
        if (singCards.isEmpty())
            return new ArrayList<>();
        
        for (ArrayList<Cards> sings : singCards) { //TUTE
            if (sings.size()==4)
                return sings;
        }
        
        for (ArrayList<Cards> sings : singCards) { //40s
            if (sings.get(0).getSuit().equals(game.getTriunfo().getSuit())) {
                this.sings.add(sings.get(0).getSuit());
                return sings;
            }
        }
        
        this.sings.add(singCards.get(0).get(0).getSuit()); //20s (da igual cual, pilla el primer palo que le llega)
        return singCards.get(0);
        
    }
    
    
    
    /**
     * Calculate the cards which can be played in the current play from the player hand.
     * @param game Game where the player is playing
     * @return a list with the playable cards
     */
    protected ArrayList<Cards> checkPlayableCards(Table game) {
        //TODOs
        return null;
    }
    
    
    /**
     * Search the cards in the player hand which can be singed.
     * 
     * In the case that the chosen one was a "Tute sing".
     * 
     * @param game Game where the player is playing
     * @return List of cards which can be singed
     */
    protected ArrayList< ArrayList<Cards> > checkSingableCards (Table game) {
        ArrayList< ArrayList<Cards> > cardsToSing = new ArrayList<>();
        
        ArrayList<Cards> horses = new ArrayList<>(); //Antes de nada, filtro las cartas de la
        ArrayList<Cards> kings  = new ArrayList<>();    //mano y dejo solo los caballos y los reyes
        for (Cards d : hand) {
            if (d.getNumber()==Numbers.HORSE) horses.add(d);
            else if (d.getNumber().equals(Numbers.KING)) kings.add(d);
        }
        
        if (kings.size()==4) {
            cardsToSing.add( kings );
            sings.addAll( Arrays.asList(Suits.values()) );
            return cardsToSing;
        }
        
        if (horses.size()==4) {
            cardsToSing.add( horses );
            sings.addAll(Arrays.asList(Suits.values()));
            return cardsToSing;
        }
        
        for (Cards h : horses) {
            if (this.sings.contains(h.getSuit())) //Si ya la ha cantado antes o si ya esta añadida a la lista de posibles cantes
                continue;
            for (Cards k : kings) {
                if (h.getSuit().equals(k.getSuit())) {
                    cardsToSing.add( new ArrayList<>(Arrays.asList(h,k)) );
                    break;
                }
            }
        }
        
        return cardsToSing;
    }
    
    
    
    public void addPoints(int points) {
        this.points += points;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public ArrayList<Cards> getHand() {
        return hand;
    }
    
    @Override
    public String toString() {
        return name.toUpperCase();
    }
    
}