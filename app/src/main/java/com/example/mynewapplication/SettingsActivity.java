package com.example.mynewapplication;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    RadioGroup levelMate;
    RadioGroup levelEnemies;
    RadioGroup numOfGames;


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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        levelMate = this.findViewById(R.id.level_mate);
        levelEnemies = this.findViewById(R.id.level_opponent);
        numOfGames = this.findViewById(R.id.num_games);

        switch (Model.instance().getNumOfGames()){
            case 1:
                numOfGames.check(R.id.num_games_1);
                break;
            case 3:
                numOfGames.check(R.id.num_games_3);
                break;
            case 5:
                numOfGames.check(R.id.num_games_5);
                break;
        }

        switch (Model.instance().getAllyDifficulty()){
            case "Easy":
                levelMate.check(R.id.mate_easy);
                break;
            case "Medium":
                levelMate.check(R.id.mate_medium);
                break;
            case "Hard":
                levelMate.check(R.id.mate_hard);
                break;
        }

        switch (Model.instance().getEnemiesDifficulty()){
            case "Easy":
                levelEnemies.check(R.id.opponent_easy);
                break;
            case "Medium":
                levelEnemies.check(R.id.opponent_medium);
                break;
            case "Hard":
                levelEnemies.check(R.id.opponent_hard);
                break;
        }

    }

    public void changedLevelMate(View view){
        int selected = levelMate.getCheckedRadioButtonId();
        RadioButton selectedRadioButton= findViewById(selected);
        Model.instance().setAllyDifficulty(selectedRadioButton.getText().toString());
    }

    public void changedLevelEnemies(View view){
        int selected = levelEnemies.getCheckedRadioButtonId();
        RadioButton selectedRadioButton= findViewById(selected);
        Model.instance().setEnemiesDifficulty(selectedRadioButton.getText().toString());
    }

    public void changedNumGames(View view){
        int selected = numOfGames.getCheckedRadioButtonId();
        RadioButton selectedRadioButton= findViewById(selected);
        Model.instance().setNumOfGames(Integer.parseInt(selectedRadioButton.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    public void menu(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}