package com.example.mynewapplication.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Game {
    
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_BLUE = "\033[0;34m";
    public static final String ANSI_RED = "\033[0;31m";
    
    private static final int INIT_HAND_CARDS = 10;
    private static final int TOTAL_PLAYS_NUMBER = INIT_HAND_CARDS;
    //private static final int INIT_SHUFFLE_PERMUTATIONS = 100; //TODO?
    
    private int FINISH_ROUND = 0;

    private int round = 0;
    private Player currentPlayer;

    private final ArrayList<Player> players;
    private final ArrayList<Player> Team1;
    private final ArrayList<Player> Team2;
    private int pointsTeam1;
    private int pointsTeam2;
    
    public Table table;
    
    public Game(ArrayList<Player> players) {
        table = new Table();
        this.players = players;
        for (Player p : players)
            p.joinGame(this);
        Team1 = new ArrayList<>();
        Team2 = new ArrayList<>();
        Team1.add(players.get(0));
        Team1.add(players.get(2));
        Team2.add(players.get(1));
        Team2.add(players.get(3));
    }

    public Game(Game game) {
        // Players copy
        this.players = new ArrayList<>();
        for (Player p : game.getPlayers()) {
            Player newPlayer = new Player(p.getName());
            p.getHand().forEach(c -> newPlayer.receiveCard(c));
            p.sings.forEach(s -> newPlayer.sings.add(s));
            newPlayer.joinGame(this);
            this.players.add(newPlayer);
        }

        // Table copy (with new players instances)
        this.table = new Table();
        game.getTable().getDeck().forEach(c -> this.table.getDeck().add(c));
        game.getTable().getTotalPlayedCards().forEach(c -> this.table.getTotalPlayedCards().add(c));
        this.table.setTriunfo(game.getTable().getTriunfo());
        for (Map.Entry<Player, Cards> entry : game.getTable().getPlayedCards().entrySet()) {
            for (Player newPlayer : this.players)
                if (newPlayer.getName().equals(entry.getKey().getName()))
                    this.table.getPlayedCards().put(newPlayer, entry.getValue());
        }

        // Teams copy (with new players instances)
        this.Team1   = new ArrayList<>();
        this.Team2   = new ArrayList<>();
        for (Player p : game.getTeam(1)) {
            for (Player newPlayer : this.players)
                if (newPlayer.getName().equals(p.getName()))
                    this.Team1.add(newPlayer);
        }
        for (Player p : game.getTeam(2)) {
            for (Player newPlayer : this.players)
                if (newPlayer.getName().equals(p.getName()))
                    this.Team2.add(newPlayer);
        }

        // Teams points copy
        this.pointsTeam1 = game.getPoints(1);
        this.pointsTeam2 = game.getPoints(2);

        // Round count copy
        this.round = game.getRound();

        // Current player copy
        for (Player newPlayer : this.players)
            if (newPlayer.getName().equals(game.getCurrentPlayer().getName()))
                this.currentPlayer = newPlayer;
    }
    
    private void finishRound(int team) {
        if (team != 0) 
            System.out.println(ANSI_RED + " ####### CONGRATULATIONS, the team " + team + " has won this round ###### " + ANSI_RESET);
        else
            System.out.println(ANSI_RED + " ####### DRAW in this round ###### " + ANSI_RESET);
        FINISH_ROUND = 1;
    }
    
    
    
    private Map.Entry<Player, Cards> checkWonCard() {
        Player jugadorGanador = null;
        
        ArrayList<Map.Entry<Player, Cards>> plays = new ArrayList<>(table.getPlayedCards().entrySet());
        for (Map.Entry<Player, Cards> entry : plays) {
            if (jugadorGanador == null) {
                jugadorGanador = entry.getKey();
                continue;
            }
            if (table.getPlayedCards().get(jugadorGanador).getSuit().equals(entry.getValue().getSuit())) {
                if (table.getPlayedCards().get(jugadorGanador).getNumber().compareTo(entry.getValue().getNumber()) < 0) {
                    jugadorGanador = entry.getKey();
                }
            } else {
                if (entry.getValue().getSuit().equals(table.getTriunfo().getSuit())) {
                    jugadorGanador = entry.getKey();
                }
            }
        }
        
        for (Map.Entry<Player, Cards> entry : table.getPlayedCards().entrySet()) {
            if (entry.getKey().equals(jugadorGanador))
                return entry;
        }
        return null;
    }
    
    
    
    public Cards checkWonCard(ArrayList<Cards> plays) {
        Cards cartaGanadora = null;
        
        for (Cards c : plays) {
            if (cartaGanadora == null) {
                cartaGanadora = plays.get(0);
                continue;
            }
            if (cartaGanadora.getSuit().equals(c.getSuit())) {
                if (cartaGanadora.getNumber().compareTo(c.getNumber()) < 0) {
                    cartaGanadora = c;
                }
            } else {
                if (c.getSuit().equals(table.getTriunfo().getSuit())) {
                    cartaGanadora = c;
                }
            }
        }
        
        return cartaGanadora;
    }
    
    
    
    public void initialDeal() {
        for (int i = 0; i < INIT_HAND_CARDS; i++) {
            for (int j = 0; j < players.size(); j++) {
                dealCard(players.get(j), (i == INIT_HAND_CARDS - 1 && j == players.size() - 1));//If is the last deal to the last player, add the Triunfo
            }
        }
    }
    
    
    
    private Cards dealCard(Player player, boolean Triunfo) {
        Cards dealingCard = table.getDeck().get(0);
        player.receiveCard(dealingCard);
        if (Triunfo) {
            table.setTriunfo(dealingCard);
        }
        table.getDeck().remove(0);
        return dealingCard;
    }
    
    
    
    private Cards askForCard(Player player) {
        return player.playCard();
    }

    
    
    private ArrayList<Cards> askForSing(Player player) {
        return player.sing();
    }
    
    
    
    public void addPoints(int team, int points) {
        if (team == 1)
            pointsTeam1 += points;
        else if (team == 2)
            pointsTeam2 += points;
    }
    
    public int getPoints(int team) {
        if (team == 1)
            return pointsTeam1;
        else if (team == 2)
            return pointsTeam2; 
        else
            return 0;
    }
    
    public void setPoints(int team, int points) {
        if (team == 1)
            pointsTeam1 = points;
        else if (team == 2)
            pointsTeam2 = points;
    }
    
    public int getTeam(Player p) {
        if (Team1.contains(p))
            return 1;
        else if (Team2.contains(p))
            return 2;
        else
            return 0;
    }

    public ArrayList<Player> getTeam(int team) {
        if (team == 1)
            return Team1;
        else if (team == 2)
            return Team2;
        else
            return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Table getTable() {
        return table;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        System.err.println("CURRENT PLAYER = " + player);
        this.currentPlayer = player;
    }

    public int getRound() {
        return round;
    }

    public void addRound() {
        round++;
    }

    public void startGameCLI() {

        initialDeal();

        System.out.println("The TRIUNFO is [" + table.getTriunfo() + "]");
        System.out.println();

        for (int i = 1; i <= TOTAL_PLAYS_NUMBER || FINISH_ROUND == 1; i++) {

            System.out.println("#######################################################################");
            System.out.println("########################## PLAY " + i + " ####################################" + ((i < 10) ? "#" : ""));
            System.out.println("#######################################################################");

            
            System.out.println("HANDS IN THE " + i + "º PLAY:");
            
            players.forEach((p) -> {System.out.println(((p instanceof Human) ? ANSI_BLUE : "") + p + "\t - \t" + p.getHand() + ((p instanceof Human) ? ANSI_RESET : ""));});
            
            System.out.println();

            
            System.out.println("TRICK FOR THE " + i + "º PLAY:");
            
            players.forEach((p) -> {
                System.out.print(((p instanceof Human) ? ANSI_BLUE : "") + p + "\t - \t");
                table.getPlayedCards().put(p, askForCard(p));
                System.out.print((p instanceof Human) ? ANSI_RESET : table.getPlayedCards().get(p) + "\n");
            });
            
            System.out.println();

            
            Map.Entry<Player, Cards> wonPlay = checkWonCard();

            System.out.println("The player " + wonPlay.getKey() + " has won the play with the card [" + wonPlay.getValue() + "]");
            System.out.println();
            
            addPoints(getTeam(wonPlay.getKey()), Cards.calculatePoints(new ArrayList<>(table.getPlayedCards().values())));

            //table.addTrick(wonPlay.getKey());

            
            System.out.println(" -> Asking the player " + wonPlay.getKey() + " for a sing");

            ArrayList<Cards> sing = askForSing(wonPlay.getKey());

            if (!sing.isEmpty()) {
                if (sing.size() == 4) {
                    System.out.println("The player " + wonPlay.getKey() + " has sung TUTE with the cards " + sing);
                    finishRound(getTeam(wonPlay.getKey()));
                } else if (sing.get(0).getSuit().equals(table.getTriunfo().getSuit())) {
                    System.out.println("The player " + wonPlay.getKey() + " has sung the 40s with the cards " + sing);
                    addPoints(getTeam(wonPlay.getKey()), 40);
                } else {
                    System.out.println("The player " + wonPlay.getKey() + " has sung the 20s with the cards " + sing);
                    addPoints(getTeam(wonPlay.getKey()), 20);
                }
            }
            System.out.println();

            
            System.out.println("CURRENT POINTS IN THE " + i + "º PLAY:");
            System.out.println( "Team 1 " + Team1 + "\t - \t" + pointsTeam1);
            System.out.println( "Team 2 " + Team2 + "\t - \t" + pointsTeam2);
            System.out.println("");
            
            Utils.rotatePlayerArray(players, wonPlay.getKey()); //put the won player as the first one to start the next play
            table.removeCurrentPlay();
            
        }

        if (pointsTeam1 > pointsTeam2)
            finishRound(1);
        else if (pointsTeam1 < pointsTeam2)
            finishRound(2);
        else
            finishRound(0);

    }


    
    
    public static void main(String[] args) {

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(
                new Human("Sergio"),
                new Player("Marcos"),
                new Player("Roi"),
                new Player("Pablo")
        ));

        Game game = new Game(players);
        
        game.startGameCLI();

    }
}