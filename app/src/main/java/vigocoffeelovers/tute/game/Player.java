package vigocoffeelovers.tute.game;

import vigocoffeelovers.tute.montecarlo.MonteCarloTreeSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Player {
    
    protected String name;
    private ArrayList<Cards> hand;
    ArrayList<Suits> sings;
    protected Game game;
    
    
    
    /**
     * Creates a player ready to play a Tute match.
     * @param name Name of the player. It will work as an identifier for him.
     */
    public Player(String name) {
        this.hand   = new ArrayList<>();
        this.sings  = new ArrayList<>();
        this.name   = name;
    }
    
    /**
     * Creates a player ready to play a Tute match.
     * @param name Name of the player. It will work as an identifier for him.
     * @param game Game the player is joining to
     */
    public Player(String name, Game game) {
        this(name);
        this.game = game;
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
     * @return the chosen card to play
     */
    public Cards playCard(int timeToThink) {
        return new MonteCarloTreeSearch().findNextPlay(game, timeToThink);
    }
    
    
    
    /**
     * Choose (if exists) a pair of singing cards to sing them in the game. Choose the best 
     * pair among all posibilities to sing.
     * (TUTE > 40s > 20s)
     * 
     * In the case that the chosen one was a "Tute sing", the method will return 4 cards (4 Horses or 4 Kings).
     * 
     * @return the list of cards to sing
     */
    public Suits sing() {
        
        ArrayList< ArrayList<Cards> > singCards = checkSingableCards();
        
        if (singCards.isEmpty())
            return null;
        
        /*for (ArrayList<Cards> s : singCards) { //ToImplement: Tute
            if (s.size()==4)
                return s;
        }*/
        
        for (ArrayList<Cards> s : singCards) { //40s
            if (s.get(0).getSuit().equals(game.table.getTriunfo().getSuit())) {
                this.sings.add(s.get(0).getSuit());
                return game.table.getTriunfo().getSuit();
            }
        }
        
        this.sings.add(singCards.get(0).get(0).getSuit());
        return singCards.get(0).get(0).getSuit();
        
    }
    
    /**
     * Search the cards in the player hand which can be singed.
     * 
     * In the case that the chosen one was a "Tute sing".
     * 
     * @return List of cards which can be singed
     */

    private ArrayList< ArrayList<Cards> > checkSingableCards() {
        
        ArrayList< ArrayList<Cards> > cardsToSing = new ArrayList<>();
        ArrayList<Cards> horses = new ArrayList<>(); //Antes de nada, filtro las cartas de la
        ArrayList<Cards> kings  = new ArrayList<>();    //mano y dejo solo los caballos y los reyes
        
        for (Cards d : hand) {
            if (d.getNumber()==Numbers.HORSE) horses.add(d);
            else if (d.getNumber().equals(Numbers.KING)) kings.add(d);
        }
        
        /*if (kings.size()==4) { //ToImplement: Tute
            cardsToSing.add( kings );
            sings.addAll( Arrays.asList(Suits.values()) );
            return cardsToSing;
        }*/
        
        /*if (horses.size()==4) { ////ToImplement: Tute
            cardsToSing.add( horses );
            sings.addAll(Arrays.asList(Suits.values()));
            return cardsToSing;
        }*/
        
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
    
    
    
    /**
     * Calculate the cards which can be played in the current play from the player hand.
     * @return a list with the playable cards
     */
    public ArrayList<Cards> checkPlayableCards() {
        
        ArrayList<Cards> playedCards    = new ArrayList<>(game.table.getPlayedCards().values());
        
        if (playedCards.isEmpty()) {
            return hand;
        }
        Cards winCard                   = game.checkWonPlay().getValue();
        Cards firstCard                 = playedCards.get(0);
        Cards Triunfo                   = game.table.getTriunfo();
        
        ArrayList<Cards> CartasParaAsistirAlPrimerJugador   = hand.stream().filter( c -> c.getSuit().equals(firstCard.getSuit())                                                                ).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Cards> CartasParaSubirALaMejorCarta       = hand.stream().filter( c -> c.getSuit().equals(winCard.getSuit())   && c.getNumber().compareTo(winCard.getNumber())   > 0         ).collect(Collectors.toCollection(ArrayList::new)   );
        ArrayList<Cards> CartasTriunfos                     = hand.stream().filter( c -> c.getSuit().equals(Triunfo.getSuit())                                                                  ).collect(Collectors.toCollection(ArrayList::new)   );

        if ( !firstCard.getSuit().equals(Triunfo.getSuit()) && winCard.getSuit().equals(Triunfo.getSuit()) ) { //si se ha fallado con triunfo
            
            if ( !CartasParaAsistirAlPrimerJugador.isEmpty() ) { //si puedo asistir

                return CartasParaAsistirAlPrimerJugador;//asisto
                
            } else {
                
                if ( !CartasParaSubirALaMejorCarta.isEmpty() ) { //si puedo subir a la mejor carta (fallada de triunfo)

                    return CartasParaSubirALaMejorCarta;
                    
                } else {
                    return hand;
                    
                }
                
            }
            
        } else {
            
            if ( !CartasParaAsistirAlPrimerJugador.isEmpty() ) { //si puedo asistir
                
                if ( !CartasParaSubirALaMejorCarta.isEmpty() ) { //Si puedes subirle al primero

                    return CartasParaSubirALaMejorCarta;
                
                } else {

                    return CartasParaAsistirAlPrimerJugador; //asisto
                        
                }
                
            } else {
                
                if ( !CartasTriunfos.isEmpty() ) { //Si puedo triunfar

                    return CartasTriunfos;
                    
                } else {

                    return hand;
                    
                }
                
            }
            
        }
        
    }
    
    
    
    void joinGame(Game game) {
        this.game = game;
    }
    
    public ArrayList<Cards> getHand() {
        return hand;
    }
    
    @Override
    public String toString() {
        return name.toUpperCase();
    }

    void removeCard(Cards c) {
        hand.remove(c);
    }

    public String getName() { return name; }
}