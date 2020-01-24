package vigocoffeelovers.tute;

/**
 *
 * @author VigoCoffeeLovers
 */
class Model {
    private static Model __instance = null;
    private String allyDifficulty;
    private String enemiesDifficulty;
    private int numOfGames;
    private boolean firstPlayerRandom;

    private Model() {
        allyDifficulty = "Medium";
        enemiesDifficulty = "Medium";
        numOfGames = 1;
        firstPlayerRandom = false;
    }

    // En caso de que no se haya declarado nunca (lo que implica que se está accediendo por pirmera vez)
    // se ponen los valore spor defecto mediente el contructor
    static Model instance() {
        if (__instance == null) {
            __instance = new Model();
        }
        return __instance;
    }

    // Mediante estas funciones podremos cambiar los ajustes //


    String getAllyDifficulty() {
        return allyDifficulty;
    }

    void setAllyDifficulty(String allyDifficulty) {
        this.allyDifficulty = allyDifficulty;
    }

    String getEnemiesDifficulty() {
        return enemiesDifficulty;
    }

    void setEnemiesDifficulty(String enemiesDifficulty) {
        this.enemiesDifficulty = enemiesDifficulty;
    }

    int getNumOfGames() {
        return numOfGames;
    }

    void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }


    boolean isFirstPlayerRandom() {
        return firstPlayerRandom;
    }

    void setFirstPlayerRandom(boolean firstPlayerRandom) {
        this.firstPlayerRandom = firstPlayerRandom;
    }
}