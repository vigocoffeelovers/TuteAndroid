package com.example.mynewapplication.game;

import com.example.mynewapplication.GameActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Table {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[34m";

    private static final int INIT_HAND_CARDS = 10;
    private static final int TOTAL_PLAYS_NUMBER = INIT_HAND_CARDS;
    //private static final int INIT_SHUFFLE_PERMUTATIONS = 100; //TODO?

    /**
     * Won plays. List of plays (set of cards) which the player has won over the
     * course of the game.
     */
    private LinkedHashMap<Player, ArrayList<ArrayList<Cards>>> tricks = new LinkedHashMap<>();//No se esta utilizando de momento, se añaden los tricks pero no se usa

    private ArrayList<Player> players;
    private ArrayList<Cards> deck; //TODO Deck class??
    private Cards Triunfo;
    private LinkedHashMap<Player, Cards> currentPlay = new LinkedHashMap<>();

    public Table(ArrayList<Player> players) {
        this.players = (ArrayList<Player>) players.clone();
        for (Player p : players) {
            tricks.put(p, new ArrayList<>());
        }
        deck = new ArrayList<>(Arrays.asList(Cards.values())); //Creates the deck
        shuffleDeck(100);
    }

    
    private void shuffleDeck(int permutations) {
        Collections.shuffle(deck);
    }

    
    public void initialDeal() {
        for (int i = 0; i < INIT_HAND_CARDS; i++) {
            for (int j = 0; j < players.size(); j++) {
                dealCard(players.get(j), (i == INIT_HAND_CARDS - 1 && j == players.size() - 1));//If is the last deal to the last player, add the Triunfo
            }
        }
    }

    
    private Cards dealCard(Player player, boolean Triunfo) {
        Cards dealingCard = deck.get(0);
        player.receiveCard(dealingCard);
        if (Triunfo) {
            this.Triunfo = dealingCard;
        }
        deck.remove(0);
        return dealingCard;
    }

    
    private Cards askForCard(Player player) { //ask for card = hit ?¿? simple language doubt
        return player.playCard(this);
    }

    
    private ArrayList<Cards> askForSing(Player player) {
        return player.sing(this);
    }

    
    private Entry<Player, Cards> checkWonPlay() {
        Player jugadorGanador = null;
        
        ArrayList<Entry<Player, Cards>> plays = new ArrayList<>(currentPlay.entrySet());

        for (Entry<Player, Cards> entry : plays) {
            if (jugadorGanador == null) {
                jugadorGanador = entry.getKey();
                continue;
            }
            if (currentPlay.get(jugadorGanador).getSuit().equals(entry.getValue().getSuit())) {
                if (currentPlay.get(jugadorGanador).getNumber().compareTo(entry.getValue().getNumber()) < 0) {//El compare no se usa igual que el compareTo, asi que igual hay que cambiarlo
                    jugadorGanador = entry.getKey();
                }
            } else {
                if (entry.getValue().getSuit().equals(Triunfo.getSuit())) {
                    jugadorGanador = entry.getKey();
                }
            }

            //System.out.println("Jugador Ganador: " + currentPlay.get(jugadorGanador).getNumber(). + " Value : " + entry.getValue());
        }
        
        for (Entry<Player, Cards> entry : currentPlay.entrySet()) {
            if (entry.getKey().equals(jugadorGanador))
                return entry;
        }
        return null;
    }

    
    private void finishRound(Player winner) {
        System.out.println(ANSI_RED + " ####### CONGRATULATIONS, the player " + winner + " has won this round ###### " + ANSI_RESET);
        System.exit(0); //TODO
    }

    
    public Cards getTriunfo() {
        return Triunfo;
    }
    
    
    public Map<Player,Cards> getPlayedCards() {
        return currentPlay;
    }

    
    public void startGame() {

        initialDeal();

        System.out.println("The TRIUNFO is [" + Triunfo + "]");
        System.out.println();
/*
        for (int i = 1; i <= TOTAL_PLAYS_NUMBER; i++) {

            System.out.println("#######################################################################");
            System.out.println("########################## PLAY " + i + " ####################################" + ((i < 10) ? "#" : ""));
            System.out.println("#######################################################################");

            System.out.println("HANDS IN THE " + i + "º PLAY:");
            players.forEach((p) -> {
                System.out.println(((p instanceof Human) ? ANSI_BLUE : "") + p + "\t - \t" + p.getHand() + ((p instanceof Human) ? ANSI_RESET : ""));
            });
            System.out.println();
            System.out.println(" -> Asking for cards...");

            System.out.println("CURRENT TRICK IN THE " + i + "º PLAY:");
            players.forEach((p) -> {
                System.out.print(((p instanceof Human) ? ANSI_BLUE : "") + p + "\t - \t");
                currentPlay.put(p, askForCard(p));
                System.out.print((p instanceof Human) ? ANSI_RESET : currentPlay.get(p) + "\n");
            });
            System.out.println();
            System.out.println(" -> Checking won trick player...");

            Entry<Player, Cards> wonPlay = checkWonPlay();

            System.out.println("The player " + wonPlay.getKey() + " has won the play with the card [" + wonPlay.getValue() + "]");
            System.out.println();

            tricks.get(wonPlay.getKey()).add(new ArrayList<>(currentPlay.values()));

            System.out.println(" -> Asking the player " + wonPlay.getKey() + " for a sing");

            ArrayList<Cards> sing = askForSing(wonPlay.getKey());

            if (!sing.isEmpty()) {
                if (sing.size() == 4) {
                    System.out.println("The player " + wonPlay.getKey() + " has sung TUTE with the cards " + sing);
                    finishRound(wonPlay.getKey());
                }
                if (sing.get(0).getSuit().equals(Triunfo.getSuit())) {
                    System.out.println("The player " + wonPlay.getKey() + " has sung the 40s with the cards " + sing);
                    wonPlay.getKey().addPoints(40);
                } else {
                    System.out.println("The player " + wonPlay.getKey() + " has sung the 20s with the cards " + sing);
                    wonPlay.getKey().addPoints(20);
                }//TODO cantar Tute (gana la partida directamente)
            }
            System.out.println();

            wonPlay.getKey().addPoints(Cards.calculatePoints(new ArrayList<>(currentPlay.values())));

            System.out.println("CURRENT POINTS IN THE " + i + "º PLAY:");
            ArrayList<Player> sortedPlayers = new ArrayList<>(players);
            sortedPlayers.sort(playersByPoints);
            sortedPlayers.forEach((Player p) -> {
                System.out.println(((p instanceof Human) ? ANSI_BLUE : "") + p + "\t - \t" + p.getPoints() + ((p instanceof Human) ? ANSI_RESET : ""));
            });
            System.out.println();

            Utils.rotatePlayerArray(players, wonPlay.getKey()); //put the won player as the first one to start the next play
            currentPlay.clear();
        }

 */

        players.sort(playersByPoints);

        finishRound(players.get(0));

    }
    
    private final static Comparator playersByPoints = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            if (o1.getPoints() > o2.getPoints()) {
                return -1;
            } else if (o1.getPoints() < o2.getPoints()) {
                return 1;
            } else {
                return 0;
            }
        }
    };
}
