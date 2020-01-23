package com.example.mynewapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynewapplication.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


public class GameActivity extends AppCompatActivity{

    //For now static constants, in future will be suitables on the setting smenu
    public final static int DECK = 0;
    public final static int GAMES_TO_WIN = 1;


    //Used to obtain the id of a clicked View (the card clicked)
    public static int clickedId;
    GameThread gameThread;
    //Our players (the human class references the real player, while the others are just the AIs)
    ArrayList<Player> players = new ArrayList<>(Arrays.asList(
            new Human("You"),
            new Player("Right Enemy"),
            new Player("Your Ally"),
            new Player("Left Enemy")
    ));

    //Cards Views (the board cards will correspond with the ORIGINAL players order -being the first one the player and continuing clockwise-)
    ImageView triunfoView;
    ImageView[] imagesHand = new ImageView[10];
    HashMap<Integer, Cards> cardsHand = new HashMap<Integer, Cards>();
    ImageView[] imagesBoard = new ImageView[4];

    ImageView[] allyCards = new ImageView[10];
    ImageView[] rightCards = new ImageView[10];
    ImageView[] leftCards = new ImageView[10];

    //Scoreboard views
    TextView allyGames;
    TextView allyPoints;
    TextView enemyGames;
    TextView enemyPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We will activate all fullscreen options for the game window
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        //Initializing the views
        triunfoView = this.findViewById(R.id.triunfo);
        imagesHand = new ArrayList<>(Arrays.asList(
                this.findViewById(R.id.carta_jp_1),
                this.findViewById(R.id.carta_jp_2),
                this.findViewById(R.id.carta_jp_3),
                this.findViewById(R.id.carta_jp_4),
                this.findViewById(R.id.carta_jp_5),
                this.findViewById(R.id.carta_jp_6),
                this.findViewById(R.id.carta_jp_7),
                this.findViewById(R.id.carta_jp_8),
                this.findViewById(R.id.carta_jp_9),
                this.findViewById(R.id.carta_jp_10)
        )).toArray(imagesHand);

        imagesBoard = new ArrayList<>(Arrays.asList(
                this.findViewById(R.id.jugada_abajo),
                this.findViewById(R.id.jugada_derecha),
                this.findViewById(R.id.jugada_arriba),
                this.findViewById(R.id.jugada_izquierda)
                )).toArray(imagesBoard);

        allyGames = this.findViewById(R.id.allyTeamGames);
        allyPoints = this.findViewById(R.id.allyTeamPoints);
        enemyGames = this.findViewById(R.id.enemyTeamGames);
        enemyPoints = this.findViewById(R.id.enemyTeamPoints);


        allyCards = new ArrayList<>(Arrays.asList(
                this.findViewById(R.id.carta_arb_1),
                this.findViewById(R.id.carta_arb_2),
                this.findViewById(R.id.carta_arb_3),
                this.findViewById(R.id.carta_arb_4),
                this.findViewById(R.id.carta_arb_5),
                this.findViewById(R.id.carta_arb_6),
                this.findViewById(R.id.carta_arb_7),
                this.findViewById(R.id.carta_arb_8),
                this.findViewById(R.id.carta_arb_9),
                this.findViewById(R.id.carta_arb_10)
        )).toArray(allyCards);


        rightCards = new ArrayList<>(Arrays.asList(
                this.findViewById(R.id.carta_drc_1),
                this.findViewById(R.id.carta_drc_2),
                this.findViewById(R.id.carta_drc_3),
                this.findViewById(R.id.carta_drc_4),
                this.findViewById(R.id.carta_drc_5),
                this.findViewById(R.id.carta_drc_6),
                this.findViewById(R.id.carta_drc_7),
                this.findViewById(R.id.carta_drc_8),
                this.findViewById(R.id.carta_drc_9),
                this.findViewById(R.id.carta_drc_10)
        )).toArray(rightCards);


        leftCards = new ArrayList<>(Arrays.asList(
                this.findViewById(R.id.carta_izq_1),
                this.findViewById(R.id.carta_izq_2),
                this.findViewById(R.id.carta_izq_3),
                this.findViewById(R.id.carta_izq_4),
                this.findViewById(R.id.carta_izq_5),
                this.findViewById(R.id.carta_izq_6),
                this.findViewById(R.id.carta_izq_7),
                this.findViewById(R.id.carta_izq_8),
                this.findViewById(R.id.carta_izq_9),
                this.findViewById(R.id.carta_izq_10)
        )).toArray(leftCards);

        gameThread = new GameThread(this);
        gameThread.start();
    }

    /* function dp to dx */
    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void setAllyCardsVisibility(int cantidadVisible){
        for(int i=1; i <= 10; i++){
            if(i <= cantidadVisible){
                ImageView view = allyCards[i-1];
                view.setVisibility(View.VISIBLE);
            } else {
                ImageView view = allyCards[i-1];
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setRightCardsVisibility(int cantidadVisible){
        for(int i=1; i <= 10; i++){
            if(i <= cantidadVisible){
                ImageView view = rightCards[i-1];
                view.setVisibility(View.VISIBLE);
            } else {
                ImageView view = rightCards[i-1];
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setLeftCardsVisibility(int cantidadVisible){
        for(int i=1; i <= 10; i++){
            if(i <= cantidadVisible){
                ImageView view = leftCards[i-1];
                view.setVisibility(View.VISIBLE);
            } else {
                ImageView view = leftCards[i-1];
                view.setVisibility(View.INVISIBLE);
            }
        }
    }
    /**
     * We will change this function to ensure the player actually wants to leave the game
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage("Are you sure to leave the game?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes, please!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("NO.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /**
     * This warning will pop up when the user tries to play enemy cards (or at lest just touch them...)
     * @param view
     */
    public void cannot_play(View view) {
        Toast.makeText(getApplicationContext(), "Just focus on your cards", Toast.LENGTH_LONG).show();
    }

    /**
     * Plays a card if is the correct time to do it (otherwise will objurgate the user)
     * @param view
     */
    public void click_card(View view) {
        if (gameThread.getNextplayer() == 0){
            clickedId = view.getId();
            Cards clickedCard = cardsHand.get(clickedId);
            if (gameThread.isCardValid(clickedCard)){
                //System.out.println("Player plays: " + clickedCard.toString());
                cardsHand.remove(clickedId);
                view.setVisibility(View.GONE);
                for (ImageView card:imagesHand) {
                    card.setBackgroundResource(0);
                    card.getLayoutParams().height = dpToPx(107);
                    card.getLayoutParams().width = dpToPx(70);
                }
                HumanPlay hp = new HumanPlay(gameThread, clickedCard);
                hp.start();
            }else {
                Toast.makeText(getApplicationContext(), "You cannot play this card", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "It is not your turn, don't be hasty!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This functions will just notify the user which player played each card on the board
     * @param view
     */
    public void jugada_arriba(View view) {
        Toast.makeText(getApplicationContext(), "Ally played cards", Toast.LENGTH_LONG).show();
    }
    public void jugada_abajo(View view) {
        Toast.makeText(getApplicationContext(), "Your played cards", Toast.LENGTH_LONG).show();
    }
    public void jugada_izquierda(View view) {
        Toast.makeText(getApplicationContext(), "Left enemy played cards", Toast.LENGTH_LONG).show();
    }
    public void jugada_derecha(View view) {
        Toast.makeText(getApplicationContext(), "Right enemy played cards", Toast.LENGTH_LONG).show();
    }

    /**
     * We could just remove this, but it still funny, isn't it?
     * @param view
     */
    public void carta_triunfo(View view) {
        Toast.makeText(getApplicationContext(), "Yeah this is the Triunfo", Toast.LENGTH_LONG).show();
    }

    //TODO: remove
    public void laderboard(View view) {
        Toast.makeText(getApplicationContext(), "You will be losing until we actually make the game, sorry", Toast.LENGTH_LONG).show();
    }

}

/**
 * TODO
 *
 */
class GameThread extends Thread {

    GameActivity gameActivity;
    int playsUntilEoRound = 3;
    int roundsUntilEoGame = 9;
    Cards[] onBoardCards = new Cards[4];
    int startingPlayer = 0;
    int nextplayer = -1;
    int allyGames = 0;
    int enemyGames = 0;
    public Game currentGame;

    ArrayList<Integer> currentHandCards = new ArrayList<>();

    public GameThread(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void run() {
        newGame();
    }

    /**
     * TODO: función de repartir
     */
    public void newGame(){
        roundsUntilEoGame = 9;
        currentGame = new Game(gameActivity.players);
        currentGame.initialDeal();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                gameActivity.triunfoView.setImageResource(currentGame.getTable().getTriunfo().getImage(gameActivity.DECK));

                gameActivity.setLeftCardsVisibility(10);
                gameActivity.setRightCardsVisibility(10);
                gameActivity.setAllyCardsVisibility(10);
                ArrayList<Cards> cartasOrdenadas = sortHandCards(currentGame.getPlayers().get(0).getHand(), currentGame.getTable().getTriunfo());

                for (int i=0; i <10 ; i++) {
                    gameActivity.imagesHand[i].setImageResource(cartasOrdenadas.get(i).getImage(gameActivity.DECK));
                    gameActivity.cardsHand.put(gameActivity.imagesHand[i].getId(), cartasOrdenadas.get(i));
                }
            }
        });
        nextplayer = startingPlayer;
        startingPlayer = Utils.nextPlayer(startingPlayer);
        newRound(currentGame);
    }

    public ArrayList<Cards> sortHandCards(ArrayList<Cards> cartas, Cards triunfo){
        ArrayList<Cards> cartasOrdenadas = new ArrayList<Cards>(); // Arraylist with the cards sorted left to right by "triunfo" and "palo"

        Collections.sort(cartas, Cards.CardsValueComparator); // Compare the cards in increasing "palo" and decreasing value

        for (Cards carta: cartas){ // First, we will save the cards of the "triunfo"
            if(carta.getSuit().equals(triunfo.getSuit())){
                cartasOrdenadas.add(carta);
            }
        }

        for (Cards carta: cartas){ // Now, we will introduce the other cards sorted
            if(!carta.getSuit().equals(triunfo.getSuit())){
                cartasOrdenadas.add(carta);
            }
        }

        return cartasOrdenadas;
    }

    public void newRound(Game game){
            playsUntilEoRound = 3;
            currentGame.table.removeCurrentPlay();
            playNextPlayer(game);
    }

    /**
     *  Asks the next player to play a card (if the player is a machine it will play it, if it is a human this function will end -see Human.playCard for more info-)
     */
    public void playNextPlayer(Game game) {
        //System.out.println("Player: " + nextplayer + " turn");
        game.setCurrentPlayer(game.getPlayers().get(nextplayer));
        ArrayList<Player> players = game.getPlayers();
        Cards playedCard = players.get(nextplayer).playCard();
        if (playedCard == null){
            ArrayList<Cards> playableCards = players.get(nextplayer).checkPlayableCards();
            Set<Integer> idRecursoSet = gameActivity.cardsHand.keySet();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (int idRecurso:idRecursoSet) {
                        ImageView view = gameActivity.findViewById(idRecurso);
                        if (playableCards.contains(gameActivity.cardsHand.get(idRecurso))){
                            view.setBackgroundResource(R.drawable.card_avaliable);
                        } else {
                            view.getLayoutParams().height = gameActivity.dpToPx(98);
                            view.getLayoutParams().width = gameActivity.dpToPx(64);
                        }
                    }
                }
            });
            return;
        }
        currentGame.table.addPlayedCard(gameActivity.players.get(nextplayer),playedCard);
        int temp = nextplayer;
        /*try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                switch (temp){
                    case 1:
                        gameActivity.setRightCardsVisibility(roundsUntilEoGame);
                    break;
                    case 2:
                        gameActivity.setAllyCardsVisibility(roundsUntilEoGame);
                    break;
                    case 3:
                        gameActivity.setLeftCardsVisibility(roundsUntilEoGame);
                    break;
                }
                gameActivity.imagesBoard[temp].setImageResource(playedCard.getImage(gameActivity.DECK));
                gameActivity.imagesBoard[temp].setVisibility(View.VISIBLE);
            }
        });
        /*try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //System.out.println("Player: " + nextplayer + " played: " + playedCard.getNumber() + " of " + playedCard.getSuit());
        onBoardCards[nextplayer] = playedCard;
        nextplayer = Utils.nextPlayer(nextplayer);
        //System.out.println("Plays until End of Round: " + playsUntilEoRound);
        if((--playsUntilEoRound) < 0) {
            endOfRound(game);
        }else{
            playNextPlayer(game);
        }
    }

    void humanPlayed(Cards playedCard){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                gameActivity.imagesBoard[0].setImageResource(playedCard.getImage(gameActivity.DECK));
                gameActivity.imagesBoard[0].setVisibility(View.VISIBLE);
            }
        });
        /*try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        onBoardCards[0] = playedCard;
        nextplayer = Utils.nextPlayer(nextplayer);
        currentGame.table.addPlayedCard(gameActivity.players.get(0),playedCard); //The user will always be player 0
        //System.out.println("Plays until End of Round: " + playsUntilEoRound);
        if((--playsUntilEoRound) < 0) {
            endOfRound(currentGame);
        }else{
            playNextPlayer(currentGame);
        }
    }

    boolean isCardValid(Cards card){
        ArrayList<Cards> playableCards = currentGame.getPlayers().get(0).checkPlayableCards();
        //System.out.println(playableCards);
        return playableCards.contains(card);
    }

    private void endOfRound(Game game) {
        String verb;
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setBoardInvisible();
        int winnerId = - 1;
        Cards wonCard = game.checkWonCard(new ArrayList<>(Arrays.asList(onBoardCards)));
        for (int i = 0; i < 4; i++) {
            if(onBoardCards[i] == wonCard){
                winnerId = i;
                break;
            }
        }
        System.out.println("The winner of the round is player: " + winnerId);
        int winnerTeam = game.getTeam(game.getPlayers().get(winnerId));
        ArrayList<Player> winnerTeamList = game.getTeam(winnerTeam);
        int extraPoints = 0;
        Suits sing1 = winnerTeamList.get(0).sing();
        Suits sing2 = winnerTeamList.get(1).sing();
        if( winnerTeamList.get(0).getName().equals("You")){
            verb=("have");
        }else{
            verb="has";
        }
        if(sing1 == (currentGame.getTable().getTriunfo().getSuit())){
            extraPoints+=40;


            final String msg = ("Player: " + winnerTeamList.get(0).getName()+ " "+ verb + " has singed 40s!!!!");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            });

        }else if(sing1!= null){
            extraPoints+=20;
            final String msg = ("Player: " + winnerTeamList.get(0).getName()+ " "+ verb +" singed 20s on " + sing1.name() + "!!!!");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            });
        }
        if( winnerTeamList.get(0).getName().equals("You")){
            verb=("have");
        }else{
            verb="has";
        }
        if(sing2 == (currentGame.getTable().getTriunfo().getSuit())){
            extraPoints+=40;
            final String msg = ("Player: " + winnerTeamList.get(1).getName()+ " "+ verb +" singed 40s!!!!");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            });
        }else if(sing2!= null){
            extraPoints+=20;
            final String msg = ("Player: " + winnerTeamList.get(1).getName()+ " "+ verb +" singed 20s on " + sing2.name()+ "!!!!");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            });
        }


        game.addPoints(game.getTeam(game.getPlayers().get(winnerId)), Cards.calculatePoints(new ArrayList<>(Arrays.asList(onBoardCards)))+extraPoints);

        updateLeaderBoard();
        if ((--roundsUntilEoGame) < 0){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.addPoints(game.getTeam(game.getPlayers().get(winnerId)), 10);



            if (winnerTeam==1){
                final String msg = ("Your team have won the 10 final points!!!!");
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                final String msg = ("Enemy has won the 10 final points!!!!");
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            endOfGame(game);
        } else {
            nextplayer = winnerId;
            game.addRound();
            newRound(game);
        }
    }

    private void updateLeaderBoard() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                gameActivity.enemyPoints.setText(Integer.toString(currentGame.getPoints(2)));
                gameActivity.allyPoints.setText(Integer.toString(currentGame.getPoints(1)));
                gameActivity.allyGames.setText(Integer.toString(allyGames));
                gameActivity.enemyGames.setText(Integer.toString(enemyGames));
            }
        });
    }

    private void setBoardInvisible() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                /*try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                for (ImageView image:gameActivity.imagesBoard) {
                    image.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void endOfGame(Game game) {
        if (currentGame.getPoints(1) > currentGame.getPoints(2)){
            allyGames++;
        } else {
            enemyGames++;
        }

        if (allyGames >= GameActivity.GAMES_TO_WIN || enemyGames >= gameActivity.GAMES_TO_WIN){
            if (allyGames > enemyGames){
                showEndDialog(1);
            } else{
                showEndDialog(2);
            }
        }else{
            newGame();
        }

    }

    private void showEndDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(gameActivity);
        if(i == 1){
            builder.setMessage("You win this time...\nDo you want another?");
        } else {
            builder.setMessage("Better luck next time..\nDo you want another?");
        }
        builder.setCancelable(true);
        builder.setPositiveButton("Oh yeah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameActivity.finish();
                gameActivity.startActivity(gameActivity.getIntent());
            }
        });
        builder.setNegativeButton("Enough", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(gameActivity, MenuActivity.class);
                gameActivity.startActivity(intent);
            }
        });
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                builder.show();
            }
        });
    }

    public int getNextplayer() {
        return nextplayer;
    }

    public void updatePlayerCards(int playedId) {
        if(currentHandCards.indexOf(Collections.min(currentHandCards)) == playedId){
            for (int i = playedId; i <10; i++){
                if (gameActivity.imagesHand[i].getVisibility() == View.VISIBLE){
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) gameActivity.imagesHand[i].getLayoutParams();
                    lp.setMargins(0,0,0,0);
                    gameActivity.imagesHand[i].setLayoutParams(lp);
                    return;
                }
            }
        }
    }
}

class HumanPlay extends Thread {


    GameThread gameThread;
    Cards playedCard;

    HumanPlay(GameThread gameThread, Cards card){
        this.gameThread = gameThread;
        this.playedCard = card;
    }

    @Override
    public void run() {
        gameThread.humanPlayed(playedCard);
    }
}
