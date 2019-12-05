package com.example.mynewapplication.game;

import com.example.mynewapplication.GameActivity;
import com.example.mynewapplication.R;
import java.util.ArrayList;

public class Human extends Player {

    public Human(String name) {
        super(name);
    }

    public Human(String name, Game game) {
        super(name, game);
    }
    
    @Override
    public Cards playCard() {
        
        ArrayList<Cards> playableCards = checkPlayableCards();
        Cards chosenCard = null;

        do {
            synchronized (GameActivity.sem){
                try{ GameActivity.sem.wait(); }
                catch (InterruptedException e) {
                    System.err.println("Hola, le cuebto. Ha tenido un error de implementación. Sí,0000 usted.");
                    continue;
                }
            }
            switch (GameActivity.clickedId){
                case (R.id.carta_jp_1):
                    chosenCard=hand.get(0);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_2):
                    chosenCard=hand.get(1);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_3):

                    chosenCard=hand.get(2);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_4):

                    chosenCard=hand.get(3);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_5):

                    chosenCard=hand.get(4);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_6):

                    chosenCard=hand.get(5);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_7):

                    chosenCard=hand.get(6);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_8):

                    chosenCard=hand.get(7);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_9):

                    chosenCard=hand.get(8);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }
                case (R.id.carta_jp_10):

                    chosenCard=hand.get(9);
                    if(playableCards.contains(chosenCard)){
                        return chosenCard;
                    }else{
                        System.err.println("No es jugable");
                        continue;
                    }


                default:
                    System.err.println("No es jugable");
                    continue;
            }

            
        } while (chosenCard == null);

        hand.remove(chosenCard);

        return chosenCard;
    }
}