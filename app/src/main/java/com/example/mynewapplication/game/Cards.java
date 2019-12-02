package com.example.mynewapplication.game;

import java.util.ArrayList;

enum Cards{
    
    ACE_COINS   (new Card(Suits.COINS,Numbers.ACE)),
    TWO_COINS   (new Card(Suits.COINS,Numbers.TWO)),
    THREE_COINS (new Card(Suits.COINS,Numbers.THREE)),
    FOUR_COINS  (new Card(Suits.COINS,Numbers.FOUR)),
    FIVE_COINS  (new Card(Suits.COINS,Numbers.FIVE)),
    SIX_COINS   (new Card(Suits.COINS,Numbers.SIX)),
    SEVEN_COINS (new Card(Suits.COINS,Numbers.SEVEN)),
    JACK_COINS  (new Card(Suits.COINS,Numbers.JACK)),
    HORSE_COINS (new Card(Suits.COINS,Numbers.HORSE)),
    KING_COINS  (new Card(Suits.COINS,Numbers.KING)),
    
    ACE_COPS    (new Card(Suits.COPS,Numbers.ACE)),
    TWO_COPS    (new Card(Suits.COPS,Numbers.TWO)),
    THREE_COPS  (new Card(Suits.COPS,Numbers.THREE)),
    FOUR_COPS   (new Card(Suits.COPS,Numbers.FOUR)),
    FIVE_COPS   (new Card(Suits.COPS,Numbers.FIVE)),
    SIX_COPS    (new Card(Suits.COPS,Numbers.SIX)),
    SEVEN_COPS  (new Card(Suits.COPS,Numbers.SEVEN)),
    JACK_COPS   (new Card(Suits.COPS,Numbers.JACK)),
    HORSE_COPS  (new Card(Suits.COPS,Numbers.HORSE)),
    KING_COPS   (new Card(Suits.COPS,Numbers.KING)),

    ACE_SWORDS  (new Card(Suits.SWORDS,Numbers.ACE)),
    TWO_SWORDS  (new Card(Suits.SWORDS,Numbers.TWO)),
    THREE_SWORDS(new Card(Suits.SWORDS,Numbers.THREE)),
    FOUR_SWORDS (new Card(Suits.SWORDS,Numbers.FOUR)),
    FIVE_SWORDS (new Card(Suits.SWORDS,Numbers.FIVE)),
    SIX_SWORDS  (new Card(Suits.SWORDS,Numbers.SIX)),
    SEVEN_SWORDS(new Card(Suits.SWORDS,Numbers.SEVEN)),
    JACK_SWORDS (new Card(Suits.SWORDS,Numbers.JACK)),
    HORSE_SWORDS(new Card(Suits.SWORDS,Numbers.HORSE)),
    KING_SWORDS (new Card(Suits.SWORDS,Numbers.KING)),

    ACE_CLUBS   (new Card(Suits.CLUBS,Numbers.ACE)),
    TWO_CLUBS   (new Card(Suits.CLUBS,Numbers.TWO)),
    THREE_CLUBS (new Card(Suits.CLUBS,Numbers.THREE)),
    FOUR_CLUBS  (new Card(Suits.CLUBS,Numbers.FOUR)),
    FIVE_CLUBS  (new Card(Suits.CLUBS,Numbers.FIVE)),
    SIX_CLUBS   (new Card(Suits.CLUBS,Numbers.SIX)),
    SEVEN_CLUBS (new Card(Suits.CLUBS,Numbers.SEVEN)),
    JACK_CLUBS  (new Card(Suits.CLUBS,Numbers.JACK)),
    HORSE_CLUBS (new Card(Suits.CLUBS,Numbers.HORSE)),
    KING_CLUBS  (new Card(Suits.CLUBS,Numbers.KING));
    
    private Cards(Card card) {
        this.card = card;
    }
    
    public int quantity() {
        return Cards.values().length;
    }
    
    public Numbers getNumber() {
        return card.getNumber();
    }

    public Suits getSuit() {
        return card.getSuit();
    }
    
    public int getPoints() {
        return card.getNumber().getPoints();
    }
    
    public static int calculatePoints(ArrayList<Cards> cards) {
        int points = 0;
        for (Cards card : cards) {
            points += card.getPoints();
        }
        return points;
    }
    
    public static Cards getCard(Numbers number, Suits suit) {
        for (Cards c : values())
            if (c.getNumber().equals(number) && c.getSuit().equals(suit))
                return c;
        return null;
    }
    
    
    
    private final Card card;
    
}

class Card {
    
    private Suits suit;
    private Numbers number;
    //private File image; //TODO

    public Card(Suits suit, Numbers number) {
        this.suit = suit;
        this.number = number;
        //this.image = image;
    }
    
//    public File getImage() {
//        return image;
//    }

    public Numbers getNumber() {
        return number;
    }

    public Suits getSuit() {
        return suit;
    }
    
}