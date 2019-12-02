package com.example.mynewapplication.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Utils {
    
    public static void rotatePlayerArray(ArrayList<Player> array, Player new_first_player) {
        ArrayList<Player> player = new ArrayList<>(Arrays.asList(new_first_player));
        Collections.rotate(array,array.size() - Collections.indexOfSubList(array, player));
    }
    
}
