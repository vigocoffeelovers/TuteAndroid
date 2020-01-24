package vigocoffeelovers.tute;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import vigocoffeelovers.tute.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


/**
 *
 * @author VigoCoffeeLovers
 */
public class GameActivity extends AppCompatActivity{

    // On future versions this will be editable
    public final int DECK = 0;

    int gamesToWin = 1;
    int allyThinkingTime = 750;
    int enemiesThinkingTime = 750;
    boolean firstPlayerRandom = false;


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

        gamesToWin = Model.instance().getNumOfGames();
        String allyDifficulty = Model.instance().getAllyDifficulty();
        switch (allyDifficulty){
            case "Easy":
                allyThinkingTime = 0;
                break;
            case "Medium":
                allyThinkingTime = 750;
                break;
            case "Hard":
                allyThinkingTime = 2000;
                break;
            default:
                break;
        }
        String enemiesDifficulty = Model.instance().getEnemiesDifficulty();
        switch (enemiesDifficulty){
            case "Easy":
                enemiesThinkingTime = 0;
                break;
            case "Medium":
                enemiesThinkingTime = 750;
                break;
            case "Hard":
                enemiesThinkingTime = 2000;
                break;
            default:
                break;
        }

        firstPlayerRandom = Model.instance().isFirstPlayerRandom();

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
        builder.setPositiveButton("Yes, please!", (dialog, which) -> {
            Intent intent = new Intent(GameActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("NO.", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    /**
     * This warning will pop up when the user tries to play enemy cards (or at lest just touch them...)
     */
    public void cannot_play(View view) {
        Toast.makeText(getApplicationContext(), "Just focus on your cards", Toast.LENGTH_LONG).show();
    }

    /**
     * Plays a card if is the correct time to do it (otherwise will objurgate the user)
     */
    public void click_card(View view) {
        if (gameThread.getNextPlayer() == 0){
            clickedId = view.getId();
            Cards clickedCard = cardsHand.get(clickedId);
            if (gameThread.isCardValid(clickedCard)){
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
     * We could just remove this, but it's still funny, isn't it?
     */
    public void carta_triunfo(View view) {
        Toast.makeText(getApplicationContext(), "Yeah this is the Triunfo", Toast.LENGTH_LONG).show();
    }
}


/**
 *
 * @author VigoCoffeeLovers
 */
class GameThread extends Thread {

    private GameActivity gameActivity;
    private int playsUntilEoRound = 3;
    private int roundsUntilEoGame = 9;
    private Cards[] onBoardCards = new Cards[4];
    private int startingPlayer = 0;
    private int nextPlayer = -1;
    private int allyGames = 0;
    private int enemyGames = 0;
    private Game currentGame;

    GameThread(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void run() {

        if (gameActivity.firstPlayerRandom){
            startingPlayer = new Random().nextInt(4);
        }
        newGame();
    }


    private void newGame(){
        roundsUntilEoGame = 9;
        currentGame = new Game(gameActivity.players);
        currentGame.initialDeal();
        updateLeaderBoard();
        new Handler(Looper.getMainLooper()).post(() -> {
            gameActivity.triunfoView.setImageResource(currentGame.getTable().getTriunfo().getImage(gameActivity.DECK));

            gameActivity.setLeftCardsVisibility(10);
            gameActivity.setRightCardsVisibility(10);
            gameActivity.setAllyCardsVisibility(10);
            ArrayList<Cards> cartasOrdenadas = sortHandCards(currentGame.getPlayers().get(0).getHand(), currentGame.getTable().getTriunfo());

            for (int i=0; i <10 ; i++) {
                gameActivity.imagesHand[i].setImageResource(cartasOrdenadas.get(i).getImage(gameActivity.DECK));
                gameActivity.cardsHand.put(gameActivity.imagesHand[i].getId(), cartasOrdenadas.get(i));
                gameActivity.imagesHand[i].setVisibility(View.VISIBLE);
            }
        });
        nextPlayer = startingPlayer;
        startingPlayer = Utils.nextPlayer(startingPlayer);
        newRound(currentGame);
    }

    private ArrayList<Cards> sortHandCards(ArrayList<Cards> cartas, Cards triunfo){
        ArrayList<Cards> cartasOrdenadas = new ArrayList<>(); // Arraylist with the cards sorted left to right by "triunfo" and "palo"

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

    private void newRound(Game game){
            playsUntilEoRound = 3;
            currentGame.table.removeCurrentPlay();
            playNextPlayer(game);
    }

    /**
     *  Asks the next player to play a card (if the player is a machine it will play it, if it is a human this function will end -see Human.playCard for more info-)
     */
    private void playNextPlayer(Game game) {
        game.setCurrentPlayer(game.getPlayers().get(nextPlayer));
        ArrayList<Player> players = game.getPlayers();
        Cards playedCard = null;
        switch (nextPlayer){
            case 0:
                ArrayList<Cards> playableCards = players.get(nextPlayer).checkPlayableCards();
                Set<Integer> idRecursoSet = gameActivity.cardsHand.keySet();
                new Handler(Looper.getMainLooper()).post(() -> {
                    for (int idRecurso:idRecursoSet) {
                        ImageView view = gameActivity.findViewById(idRecurso);
                        if (playableCards.contains(gameActivity.cardsHand.get(idRecurso))){
                            view.setBackgroundResource(R.drawable.card_avaliable);
                        } else {
                            view.getLayoutParams().height = gameActivity.dpToPx(98);
                            view.getLayoutParams().width = gameActivity.dpToPx(64);
                        }
                    }
                });
                return;
            case 1:
            case 3:
                // This will prevent too fast animations when time to think is low
                if (gameActivity.enemiesThinkingTime < 500){
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                playedCard = players.get(nextPlayer).playCard(gameActivity.enemiesThinkingTime);
                break;
            case 2:
                // This will prevent too fast animations when time to think is low
                if (gameActivity.allyThinkingTime < 500){
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                playedCard = players.get(nextPlayer).playCard(gameActivity.allyThinkingTime);
                break;
        }
        currentGame.table.addPlayedCard(gameActivity.players.get(nextPlayer),playedCard);
        int temp_nextPlayer = nextPlayer;
        Cards temp_playedCard = playedCard;

        new Handler(Looper.getMainLooper()).post(() -> {
            switch (temp_nextPlayer){
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
            gameActivity.imagesBoard[temp_nextPlayer].setImageResource(temp_playedCard.getImage(gameActivity.DECK));
            gameActivity.imagesBoard[temp_nextPlayer].setVisibility(View.VISIBLE);
        });

        onBoardCards[nextPlayer] = playedCard;
        nextPlayer = Utils.nextPlayer(nextPlayer);
        if((--playsUntilEoRound) < 0) {
            endOfRound(game);
        }else{
            playNextPlayer(game);
        }
    }

    void humanPlayed(Cards playedCard){

        new Handler(Looper.getMainLooper()).post(() -> {
            gameActivity.imagesBoard[0].setImageResource(playedCard.getImage(gameActivity.DECK));
            gameActivity.imagesBoard[0].setVisibility(View.VISIBLE);
        });

        onBoardCards[0] = playedCard;
        nextPlayer = Utils.nextPlayer(nextPlayer);
        currentGame.table.addPlayedCard(gameActivity.players.get(0),playedCard); //The user will always be player 0
        if((--playsUntilEoRound) < 0) {
            endOfRound(currentGame);
        }else{
            playNextPlayer(currentGame);
        }
    }

    boolean isCardValid(Cards card){
        ArrayList<Cards> playableCards = currentGame.getPlayers().get(0).checkPlayableCards();
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
        ArrayList<Cards> list_onBoardCards = new ArrayList<>(Arrays.asList(onBoardCards));

        Collections.rotate(list_onBoardCards, 4 - nextPlayer);
        Cards wonCard = game.checkWonCard(list_onBoardCards);
        for (int i = 0; i < 4; i++) {
            if(onBoardCards[i] == wonCard){
                winnerId = i;
                break;
            }
        }
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
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show());

        }else if(sing1!= null){
            extraPoints+=20;
            final String msg = ("Player: " + winnerTeamList.get(0).getName()+ " "+ verb +" singed 20s on " + sing1.name() + "!!!!");
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show());
        }
        if( winnerTeamList.get(0).getName().equals("You")){
            verb=("have");
        }else{
            verb="has";
        }
        if(sing2 == (currentGame.getTable().getTriunfo().getSuit())){
            extraPoints+=40;
            final String msg = ("Player: " + winnerTeamList.get(1).getName()+ " "+ verb +" singed 40s!!!!");
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show());
        }else if(sing2!= null){
            extraPoints+=20;
            final String msg = ("Player: " + winnerTeamList.get(1).getName()+ " "+ verb +" singed 20s on " + sing2.name()+ "!!!!");
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show());
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
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show());
            }else{
                final String msg = ("Enemy has won the 10 final points!!!!");
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(gameActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show());
            }

            updateLeaderBoard();

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            endOfGame(winnerTeam);
        } else {
            nextPlayer = winnerId;
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
        new Handler(Looper.getMainLooper()).post(() -> {
            for (ImageView image:gameActivity.imagesBoard) {
                image.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void endOfGame(int last10pointsTeam) {
        if (currentGame.getPoints(1) > currentGame.getPoints(2)){
            allyGames++;
            if (currentGame.getPoints(1) > 100){
                allyGames++;
            }
        } else if (currentGame.getPoints(1) < currentGame.getPoints(2)){
            enemyGames++;
            if (currentGame.getPoints(2) > 100){
                enemyGames++;
            }
        } else {
            if(last10pointsTeam==1){
                allyGames++;
                if (currentGame.getPoints(1) > 100){
                    allyGames++;
                }
            } else {
                enemyGames++;
                if (currentGame.getPoints(2) > 100){
                    enemyGames++;
                }
            }
        }


        updateLeaderBoard();

        if ((allyGames >= gameActivity.gamesToWin) || (enemyGames >= gameActivity.gamesToWin)){
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
        builder.setCancelable(false);
        builder.setPositiveButton("Oh yeah", (dialog, which) -> {
            gameActivity.finish();
            gameActivity.startActivity(gameActivity.getIntent());
        });
        builder.setNegativeButton("Enough", (dialog, which) -> {
            Intent intent = new Intent(gameActivity, MenuActivity.class);
            gameActivity.startActivity(intent);
        });
        new Handler(Looper.getMainLooper()).post(builder::show);
    }

    int getNextPlayer() {
        return nextPlayer;
    }
}

class HumanPlay extends Thread {


    private GameThread gameThread;
    private Cards playedCard;

    HumanPlay(GameThread gameThread, Cards card){
        this.gameThread = gameThread;
        this.playedCard = card;
    }

    @Override
    public void run() {
        gameThread.humanPlayed(playedCard);
    }
}
