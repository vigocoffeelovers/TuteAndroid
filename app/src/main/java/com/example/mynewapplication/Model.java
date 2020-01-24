package com.example.mynewapplication;

/**
 * Esta clase almacenará los ajustes realizados en la pestaña de settings
 */
public class Model {
    private static Model __instance = null;
    private String allyDifficulty;
    private String enemiesDifficulty;
    private int numOfGames;
    private boolean firstPlayerRandom;

    private Model() {
        allyDifficulty = "Medium";
        enemiesDifficulty = "Medium";
        numOfGames = 7;
        firstPlayerRandom = false;
    }

    // En caso de que no se haya declarado nunca (lo que implica que se está accediendo por pirmera vez)
    // se ponen los valore spor defecto mediente el contructor
    public static Model instance() {
        if (__instance == null) {
            __instance = new Model();
        }
        return __instance;
    }

    // Mediante estas funciones podremos cambiar los ajustes //


    public String getAllyDifficulty() {
        return allyDifficulty;
    }

    public void setAllyDifficulty(String allyDifficulty) {
        this.allyDifficulty = allyDifficulty;
    }

    public String getEnemiesDifficulty() {
        return enemiesDifficulty;
    }

    public void setEnemiesDifficulty(String enemiesDifficulty) {
        this.enemiesDifficulty = enemiesDifficulty;
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }


    public boolean isFirstPlayerRandom() {
        return firstPlayerRandom;
    }

    public void setFirstPlayerRandom(boolean firstPlayerRandom) {
        this.firstPlayerRandom = firstPlayerRandom;
    }
}