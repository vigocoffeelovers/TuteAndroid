package vigocoffeelovers.tute.montecarlo;

import vigocoffeelovers.tute.game.*;

import java.util.ArrayList;

/**
 *
 * @author VigoCoffeeLovers
 */
public class State {

    private Game game;
    private int visitCount;
    private double winScore;

    State(Game game) {
        this.game = new Game(game);
    }

    State(State state) {
        this.game = new Game(state.getGame());
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    ArrayList<State> getAllPossibleStates() {
        ArrayList<State> possibleStates = new ArrayList<>();
        Player currentPlayer = game.getCurrentPlayer();
        ArrayList<Cards> playableCards = currentPlayer.checkPlayableCards();
        for (int i=0;i<playableCards.size(); i++) {
            State newState = new State(game);
            Game copyGame = newState.getGame();
            copyGame.getTable().addPlayedCard(currentPlayer, playableCards.get(i));
            possibleStates.add(newState);
        }
        return possibleStates;
    }

    public Game getGame() {
        return game;
    }

    int getVisitCount() { return visitCount; }

    void incrementVisit() { this.visitCount++; }

    double getWinScore() { return winScore; }

    void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE)
            this.winScore += score;
    }

}