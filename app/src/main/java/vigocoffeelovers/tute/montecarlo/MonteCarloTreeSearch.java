package vigocoffeelovers.tute.montecarlo;

import vigocoffeelovers.tute.game.Cards;
import vigocoffeelovers.tute.game.Game;
import vigocoffeelovers.tute.game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author VigoCoffeeLovers
 */
public class MonteCarloTreeSearch {

    private static final int WIN_SCORE = 10;

    /**
     *
     * @param game the game
     * @param timeToThink on miliseconds
     */
    public Cards findNextPlay(Game game, int timeToThink) {

        long start = System.currentTimeMillis();
        long end = start + timeToThink;

        Tree tree = new Tree(game);
        Node rootNode = tree.getRoot();

        if (rootNode.childArray.isEmpty())
            expandNode(rootNode);

        do {
            Node nodeToExplore = rootNode.getRandomChildNode();
            int hasWon = simulateRandomGame(nodeToExplore);
            backPropogation(nodeToExplore, hasWon);
        } while (System.currentTimeMillis() < end);

        String currentPlayerName = game.getCurrentPlayer().getName();
        Node winnerNode = UCT.findBestNodeWithUCT(rootNode);
        tree.setRoot(winnerNode);
        Cards winnerCard = null;
        Map<Player,Cards> playedCardsInWinnerNode = winnerNode.getState().getGame().getTable().getPlayedCards();
        for (Map.Entry<Player,Cards> e : playedCardsInWinnerNode.entrySet())
            if(e.getKey().getName().equals(currentPlayerName))
                winnerCard = e.getValue();

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

    private void backPropogation(Node nodeToExplore, int hasWon) {
        nodeToExplore.getState().incrementVisit();
        nodeToExplore.getState().addScore(hasWon);
    }

    private int simulateRandomGame(Node nodeToExplore) {

        Game copyGame = new Game(nodeToExplore.getState().getGame()); //Creo una copia del estado actual del juego para poder simular sin alterar el original

        Player me = copyGame.getCurrentPlayer();
        int my_team = copyGame.getTeam(me);

        Map<Player, Cards> playedCards = copyGame.getTable().getPlayedCards(); //Cojo las cartas ya jugadas en esta ronda/vuelta

        ArrayList<Cards> totalPlayableCardsInGame = new ArrayList<>(Arrays.asList(Cards.values())); //Cojo todas las cartas que aun no han salido en la partida
        totalPlayableCardsInGame.removeAll(copyGame.getTable().getTotalPlayedCards());              //
        totalPlayableCardsInGame.removeAll(new ArrayList<>(playedCards.values()));                  //Incluidas las que ya se han jugado en esta ronda

        for (Player p : copyGame.getPlayers()) { //Reparto cartas aleatorias a los demas jugadores para simular
            if (!p.getName().equals(copyGame.getCurrentPlayer().getName())) {
                p.getHand().clear(); //Elimino la mano de los otros jugadores ya que no deberia conocerlas
                if (playedCards.get(p) == null)
                    for (int i = 0; i < 10 - copyGame.getRound(); i++) {
                        p.receiveCard(totalPlayableCardsInGame.get( new Random().nextInt(totalPlayableCardsInGame.size())) );
                    }
                else
                    for (int i = 1; i < 10 - copyGame.getRound(); i++) {
                        p.receiveCard(totalPlayableCardsInGame.get( new Random().nextInt(totalPlayableCardsInGame.size())) );
                    }
            }
        }

        for (; copyGame.getRound()<10; copyGame.addRound()) {
            simulateRandomRound(copyGame);
            copyGame.table.removeCurrentPlay();
        }

        return copyGame.getPoints(my_team) - copyGame.getPoints(my_team == 1 ? 2 : 1);

    }

    private void simulateRandomRound(Game copyGame) {
        for (Player p : copyGame.getPlayers()) {
            if (copyGame.table.getPlayedCards().get(p) == null) { //Compruebo que jugadores faltan por jugar para acabar la ronda
                ArrayList<Cards> playableCards = p.checkPlayableCards(); //Escojo las cartas que pueden ser jugadas en esta ronda
                copyGame.table.getPlayedCards().put(p, playableCards.get((int)(Math.random()*playableCards.size()))); //Hago que el jugador eche una carta al azar en la mesa de entre las que puede jugar
            }
        }
        Map.Entry<Player, Cards> winnerPlay = copyGame.checkWonPlay(); //Comprueba la carta ganadora de entre las 4 jugadas
        copyGame.addPoints(copyGame.getTeam(winnerPlay.getKey()), Cards.calculatePoints(new ArrayList<>(copyGame.table.getPlayedCards().values())));
    }

}
