package com.example.mynewapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynewapplication.game.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {


    ArrayList<Player> players = new ArrayList<>(Arrays.asList(
            new Player("Sergio"),
            new Player("Marcos"),
            new Player("Roi"),
            new Player("Pablo")
    ));

    Game game = new Game(players);
    Table table = game.getTable();

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

        ImageView triunfoView = this.findViewById(R.id.triunfo);

        table.initialDeal();
        triunfoView.setImageResource(table.getTriunfo().getImage(0));
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

    public void play_card(View view) {
        Toast.makeText(getApplicationContext(), "Yep, that's a card, well touched", Toast.LENGTH_LONG).show();
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
}
