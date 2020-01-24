package vigocoffeelovers.tute.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author VigoCoffeeLovers
 */
public final class Utils {
    
    public static void rotatePlayerArray(ArrayList<Player> array, Player new_first_player) {
        ArrayList<Player> player = new ArrayList<>(Arrays.asList(new_first_player));
        Collections.rotate(array,array.size() - Collections.indexOfSubList(array, player));
    }

    /**
     * Sets the next player id
     * @param id current next player
     * @return
     */
    public static int nextPlayer(int id){
        if (id == 3){
            id = 0;
        } else {
            id++;
        }
        return id;
    }
}
