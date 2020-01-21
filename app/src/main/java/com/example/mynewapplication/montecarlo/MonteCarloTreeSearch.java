package com.example.mynewapplication.montecarlo;

import com.example.mynewapplication.game.Cards;
import com.example.mynewapplication.game.Game;
import com.example.mynewapplication.game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MonteCarloTreeSearch {

    private static final int WIN_SCORE = 10;

    public Cards findNextPlay(Game game) {

        long start = System.currentTimeMillis();
        long end = start + 500;

        Tree tree = new Tree(game);
        Node rootNode = tree.getRoot();

        if (rootNode.childArray.isEmpty())
            expandNode(rootNode);

        while (System.currentTimeMillis() < end) {

            Node nodeToExplore = rootNode.getRandomChildNode();
            Boolean hasWon = simulateRandomPlayout(nodeToExplore); //Simulamos la jugada completa(de momento solo una "vuelta")
            backPropogation(nodeToExplore, hasWon);
        }

        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        String currentPlayerName = game.getCurrentPlayer().getName();
        Cards winnerCard = null;
        Map<Player,Cards> playedCardsInWinnerNode = winnerNode.getState().getGame().getTable().getPlayedCards();
        for (Map.Entry<Player,Cards> e : playedCardsInWinnerNode.entrySet())
            if(e.getKey().getName().equals(currentPlayerName))
                winnerCard = e.getValue();
        System.out.println("CARTA ESCOGIDA = " + winnerCard);
        return winnerCard;

    }

    private void expandNode(Node node) {
        ArrayList<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            node.getChildArray().add(newNode);
        });
    }

    private void backPropogation(Node nodeToExplore, boolean hasWon) {
        Node tempNode = nodeToExplore;
        tempNode.getState().incrementVisit();
        if (hasWon) // He ganado, entonces recompenso jugar esta carta
            tempNode.getState().addScore(WIN_SCORE);
    }

    private boolean simulateRandomPlayout(Node nodeToExplore) {

        Game copyGame = new Game(nodeToExplore.getState().getGame()); //Creo una copia del estado actual del juego para poder simular sin alterar el original

        Map<Player, Cards> playedCards = copyGame.getTable().getPlayedCards(); //Cojo las cartas ya jugadas en esta ronda/vuelta

        ArrayList<Cards> totalPlayableCardsInGame = new ArrayList<>(Arrays.asList(Cards.values())); //Cojo todas las cartas que aun no han salido en la partida
        totalPlayableCardsInGame.removeAll(copyGame.getTable().getTotalPlayedCards());

        copyGame.getPlayers().forEach(p -> {
            if (playedCards.get(p) == null) { //Compruebo que jugadores faltan por jugar para acabar la ronda/vuelta
                p.getHand().clear(); //Elimino la mano de el jugador ya que no deberia conocerla
                for (int i=0; i<11-copyGame.getRound(); i++) { p.receiveCard(totalPlayableCardsInGame.get((int)(Math.random()*totalPlayableCardsInGame.size()))); } //Le doy cartas al azar de entre las que aun no han salido
                ArrayList<Cards> playableCards = p.checkPlayableCards(); //De esa nueva mano aleatorio escojo las cartas que pueden ser jugadas en esta jugada
                playedCards.put(p, playableCards.get((int)(Math.random()*playableCards.size()))); //Hago que el jugador eche una carta al azar en la mesa de entre las que puede jugar
            }
        });

        Cards winnerCard = copyGame.checkWonCard(new ArrayList<>(playedCards.values())); //Comprueba la carta ganadora de entre las 4 jugadas

        if (winnerCard.equals(playedCards.get(copyGame.getCurrentPlayer()))) { //TODO tener en cuenta tambien su compañero de equipo
            return true;
        } else {
            return false;
        }
    }

}
