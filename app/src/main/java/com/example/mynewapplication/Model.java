package com.example.mynewapplication;

/**
 * Esta clase almacenará los ajustes realizados en la pestaña de settings
 */
public class Model {
    private static Model __instance = null;
    private String allyDificulty;
    private String enemiesDeficulty;
    private int numOfGames;


    private Model() {
        allyDificulty = "Medium";
        enemiesDeficulty = "Medium";
        numOfGames = 7;
    }

    // En caso de que no se haya declarado nunca (lo que implica que se está accediendo por pirmera vez)
    // se ponen los valore spor defecto mediente el contructor
    public static Model instance() {
        if (__instance == null) {
            __instance = new Model();
        }
        return __instance;
    }

    // Mediante estas funciones podremos cambiar los ajustes
    public String getAllyDificulty() {
        return allyDificulty;
    }

    public void setAllyDificulty(String allyDificulty) {
        this.allyDificulty = allyDificulty;
    }

    public String getEnemiesDeficulty() {
        return enemiesDeficulty;
    }

    public void setEnemiesDeficulty(String enemiesDeficulty) {
        this.enemiesDeficulty = enemiesDeficulty;
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }
}