package com.example.mynewapplication.montecarlo;

import com.example.mynewapplication.game.*;

import java.util.ArrayList;

public class State {

    private Game game;
    private int visitCount;
    private double winScore;

    public State(Game game) {
        this.game = new Game(game);
    }

    public State(State state) {
        this.game = new Game(state.getGame());
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    public ArrayList<State> getAllPossibleStates() {
        ArrayList<State> possibleStates = new ArrayList<>();
        Player currentPlayer = game.getCurrentPlayer();
        ArrayList<Cards> playableCards = currentPlayer.checkPlayableCards();
        System.err.println("PLAYABLECARDS = " + playableCards);
        for (int i=0;i<playableCards.size(); i++) { //TODO si aqui se pone un for-each, a la segunda ronda/vuelta salta una excepcion de tipo [java.util.ConcurrentModificationException]
            State newState = new State(game);
            Game copyGame = newState.getGame();
            copyGame.getTable().addPlayedCard(currentPlayer, playableCards.get(i));
            //TODO enough?
            possibleStates.add(newState);
        }
        return possibleStates;
    }

    public Game getGame() {
        return game;
    }

    public int getVisitCount() { return visitCount; }

    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }

    public void incrementVisit() { this.visitCount++; }

    public double getWinScore() { return winScore; }

    public void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE) //TODO ?¿?
            this.winScore += score;
    }

}