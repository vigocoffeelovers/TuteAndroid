package com.example.mynewapplication;

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
import android.widget.Toast;

import com.example.mynewapplication.game.*;

import java.util.ArrayList;
import java.util.Arrays;



public class GameActivity extends AppCompatActivity{
    public final static int SKIN_ID = 0;

    public static int clickedId;
    public static final Object sem = new Object();
    ArrayList<Player> players = new ArrayList<>(Arrays.asList(
            new Human("Sergio"),
            new Player("Marcos"),
            new Player("Roi"),
            new Player("Pablo")
    ));


    Game game;
    Table table;
    ImageView triunfoView;
    ImageView[] imagesHand = new ImageView[10];
    ImageView[] imagesBoard = new ImageView[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        game = new Game(players,triunfoView,imagesHand,imagesBoard);
        table = game.getTable();


    }




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

    public void sing_40(View view) {
        VivaCacha v = new VivaCacha(triunfoView);
        v.start();
        Toast.makeText(getApplicationContext(), "You sang the 40s, Congrats!!", Toast.LENGTH_LONG).show();
    }

    public void sing_20(View view) {
        Toast.makeText(getApplicationContext(), "You sang the 20s, Congrats!!", Toast.LENGTH_LONG).show();
    }

    public void sing_tute(View view) {
        Toast.makeText(getApplicationContext(), "TUTE!! Are you cheating??", Toast.LENGTH_LONG).show();
    }

    public void cannot_play(View view) {
        Toast.makeText(getApplicationContext(), "Just focus on your cards", Toast.LENGTH_LONG).show();
    }

    public void click_card(View view) {
        if (game.getTurn()==0){
            clickedId = view.getId();
            synchronized (sem){
                notify();
            }
        }else {
            Toast.makeText(getApplicationContext(), "It is not your turn, don't be hasty!", Toast.LENGTH_LONG).show();
        }
    }

    public void jugada_arriba(View view) {
        Toast.makeText(getApplicationContext(), "Here will be the ally played cards", Toast.LENGTH_LONG).show();
    }

    public void jugada_abajo(View view) {
        Toast.makeText(getApplicationContext(), "Here will be your played cards", Toast.LENGTH_LONG).show();
    }

    public void jugada_izquierda(View view) {
        Toast.makeText(getApplicationContext(), "Here will be the left enemy played cards", Toast.LENGTH_LONG).show();
    }

    public void jugada_derecha(View view) {
        Toast.makeText(getApplicationContext(), "Here will be the right enemy played cards", Toast.LENGTH_LONG).show();
    }

    public void carta_triunfo(View view) {
        Toast.makeText(getApplicationContext(), "Yeah this is the Triunfo", Toast.LENGTH_LONG).show();
    }

    public void laderboard(View view) {
        Toast.makeText(getApplicationContext(), "You will be losing until we actually make the game, sorry", Toast.LENGTH_LONG).show();
    }

    public void sacarTexto(){
        Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_LONG).show();
    }

}

class VivaCacha extends Thread {

    ImageView cacha;

    public VivaCacha(ImageView cacha) {
        this.cacha = cacha;
    }

    @Override
    public void run() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                cacha.setImageResource(R.drawable.cacha);
            }
        });
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                cacha.setImageResource(R.drawable.sergio);
            }
        });
    }
}
