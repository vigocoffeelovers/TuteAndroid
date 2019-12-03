package com.example.mynewapplication.game;

import android.graphics.Bitmap;

import com.example.mynewapplication.R;

import java.util.ArrayList;

public enum Cards{

    ACE_COINS   (new Card(Suits.COINS,Numbers.ACE, new int[]{R.drawable.cards_coin1})),
    TWO_COINS   (new Card(Suits.COINS,Numbers.TWO,new int[]{R.drawable.cards_coin2})),
    THREE_COINS (new Card(Suits.COINS,Numbers.THREE,new int[]{R.drawable.cards_coin3})),
    FOUR_COINS  (new Card(Suits.COINS,Numbers.FOUR,new int[]{R.drawable.cards_coin4})),
    FIVE_COINS  (new Card(Suits.COINS,Numbers.FIVE,new int[]{R.drawable.cards_coin5})),
    SIX_COINS   (new Card(Suits.COINS,Numbers.SIX,new int[]{R.drawable.cards_coin6})),
    SEVEN_COINS (new Card(Suits.COINS,Numbers.SEVEN,new int[]{R.drawable.cards_coin7})),
    JACK_COINS  (new Card(Suits.COINS,Numbers.JACK,new int[]{R.drawable.cards_coin10})),
    HORSE_COINS (new Card(Suits.COINS,Numbers.HORSE,new int[]{R.drawable.cards_coin11})),
    KING_COINS  (new Card(Suits.COINS,Numbers.KING,new int[]{R.drawable.cards_coin12})),

    ACE_CUPS    (new Card(Suits.CUPS,Numbers.ACE,new int[]{R.drawable.cards_cup1})),
    TWO_CUPS    (new Card(Suits.CUPS,Numbers.TWO,new int[]{R.drawable.cards_cup2})),
    THREE_CUPS  (new Card(Suits.CUPS,Numbers.THREE,new int[]{R.drawable.cards_cup3})),
    FOUR_CUPS   (new Card(Suits.CUPS,Numbers.FOUR,new int[]{R.drawable.cards_cup4})),
    FIVE_CUPS   (new Card(Suits.CUPS,Numbers.FIVE,new int[]{R.drawable.cards_cup5})),
    SIX_CUPS    (new Card(Suits.CUPS,Numbers.SIX,new int[]{R.drawable.cards_cup6})),
    SEVEN_CUPS  (new Card(Suits.CUPS,Numbers.SEVEN,new int[]{R.drawable.cards_cup7})),
    JACK_CUPS   (new Card(Suits.CUPS,Numbers.JACK,new int[]{R.drawable.cards_cup10})),
    HORSE_CUPS  (new Card(Suits.CUPS,Numbers.HORSE,new int[]{R.drawable.cards_cup11})),
    KING_CUPS   (new Card(Suits.CUPS,Numbers.KING,new int[]{R.drawable.cards_cup12})),

    ACE_SWORDS  (new Card(Suits.SWORDS,Numbers.ACE,new int[]{R.drawable.cards_sword1})),
    TWO_SWORDS  (new Card(Suits.SWORDS,Numbers.TWO,new int[]{R.drawable.cards_sword2})),
    THREE_SWORDS(new Card(Suits.SWORDS,Numbers.THREE,new int[]{R.drawable.cards_sword3})),
    FOUR_SWORDS (new Card(Suits.SWORDS,Numbers.FOUR,new int[]{R.drawable.cards_sword4})),
    FIVE_SWORDS (new Card(Suits.SWORDS,Numbers.FIVE,new int[]{R.drawable.cards_sword5})),
    SIX_SWORDS  (new Card(Suits.SWORDS,Numbers.SIX,new int[]{R.drawable.cards_sword6})),
    SEVEN_SWORDS(new Card(Suits.SWORDS,Numbers.SEVEN,new int[]{R.drawable.cards_sword7})),
    JACK_SWORDS (new Card(Suits.SWORDS,Numbers.JACK,new int[]{R.drawable.cards_sword10})),
    HORSE_SWORDS(new Card(Suits.SWORDS,Numbers.HORSE,new int[]{R.drawable.cards_sword11})),
    KING_SWORDS (new Card(Suits.SWORDS,Numbers.KING,new int[]{R.drawable.cards_sword12})),

    ACE_CLUBS   (new Card(Suits.CLUBS,Numbers.ACE,new int[]{R.drawable.cards_club1})),
    TWO_CLUBS   (new Card(Suits.CLUBS,Numbers.TWO,new int[]{R.drawable.cards_club2})),
    THREE_CLUBS (new Card(Suits.CLUBS,Numbers.THREE,new int[]{R.drawable.cards_club3})),
    FOUR_CLUBS  (new Card(Suits.CLUBS,Numbers.FOUR,new int[]{R.drawable.cards_club4})),
    FIVE_CLUBS  (new Card(Suits.CLUBS,Numbers.FIVE,new int[]{R.drawable.cards_club5})),
    SIX_CLUBS   (new Card(Suits.CLUBS,Numbers.SIX,new int[]{R.drawable.cards_club6})),
    SEVEN_CLUBS (new Card(Suits.CLUBS,Numbers.SEVEN,new int[]{R.drawable.cards_club7})),
    JACK_CLUBS  (new Card(Suits.CLUBS,Numbers.JACK,new int[]{R.drawable.cards_club10})),
    HORSE_CLUBS (new Card(Suits.CLUBS,Numbers.HORSE,new int[]{R.drawable.cards_club11})),
    KING_CLUBS  (new Card(Suits.CLUBS,Numbers.KING,new int[]{R.drawable.cards_club12}));

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

    public int getImage(int deck) {
        return card.getImage(deck);
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
    private int [] image; //TODO

    public Card(Suits suit, Numbers number, int[] image) {
        this.suit = suit;
        this.number = number;
        this.image = image;
    }

    public int getImage(int deck) {
        return image[deck];
    }

    public Numbers getNumber() {
        return number;
    }

    public Suits getSuit() {
        return suit;
    }

}