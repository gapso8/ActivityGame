package com.example.gaper.activity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gašper on 01/07/2018.
 */

public class Team implements Serializable{
    String name;
    int color;
    String player1;
    String player2;
    ArrayList values;

    public Team(String name, int color, String player1, String player2, ArrayList values){
        this.name = name;
        this.color = color;
        this.player1 = player1;
        this.player2 = player2;
        this.values = values;
    }

}
