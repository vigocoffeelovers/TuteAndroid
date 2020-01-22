package com.example.mynewapplication.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Table {

    private ArrayList<Cards> totalPlayedCards;

    //private LinkedHashMap<Player, ArrayList<ArrayList<Cards>>> tricks;//No se esta utilizando de momento, se añaden los tricks pero no se usa
    private ArrayList<Cards> deck;
    private Cards Triunfo;
    private LinkedHashMap<Player, Cards> currentPlay;

    public Table() {
        this.currentPlay = new LinkedHashMap<>();
        this.totalPlayedCards = new ArrayList<>();
        //this.tricks = new LinkedHashMap<>();
        //players.forEach((Player p) -> tricks.put(p, new ArrayList<>()));
        deck = new ArrayList<>(Arrays.asList(Cards.values())); //Creates the deck
        shuffleDeck();
    }
    
    private void shuffleDeck() {
        Collections.shuffle(deck);
    }
    
    
    public void setTriunfo(Cards triunfo) {
        this.Triunfo = triunfo;
    }
    
    
    public Cards getTriunfo() {
        return Triunfo;
    }

    public void addPlayedCard(Player p, Cards c) {
        totalPlayedCards.add(c);
        p.removeCard(c);
        currentPlay.put(p,c);
    }
    
    
    public Map<Player,Cards> getPlayedCards() {
        return currentPlay;
    }
    
    public void removeCurrentPlay() {
        currentPlay.clear();
    }
    
//    public void addTrick(Player p) {
//        tricks.get(p).add(new ArrayList(currentPlay.values()));
//        removeCurrentPlay();
//    }
    
    public ArrayList<Cards> getDeck() {
        return deck;
    }

    public ArrayList<Cards> getTotalPlayedCards() {
        return totalPlayedCards;
    }
}
