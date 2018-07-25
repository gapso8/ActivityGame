package com.example.gaper.activity;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EndActivity extends PlayActivity {

    private ArrayList values1;
    private ArrayList values2;

    Team myTeam1;
    Team myTeam2;

    TextView stats1;
    TextView stats2;

    ConstraintLayout firstLL;
    ConstraintLayout secondLL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        firstLL = (ConstraintLayout) findViewById(R.id.firstLL);
        secondLL = (ConstraintLayout) findViewById(R.id.secondLL);

        stats1 = (TextView) findViewById(R.id.stats1);
        stats2 = (TextView) findViewById(R.id.stats2);

        Bundle myBundle = getIntent().getExtras();

        if (myBundle != null) {

            myTeam1 = (Team) myBundle.getSerializable("team1");
            myTeam2 = (Team) myBundle.getSerializable("team2");

            values1 = myTeam1.values;
            values2 = myTeam2.values;


            setBackgroundColor();

            setStats();
        }

    }



    private void setBackgroundColor() {

        switch (myTeam1.color) {
            case "red":
                firstLL.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case "green":
                firstLL.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case "blue":
                firstLL.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case "black":
                firstLL.setBackgroundColor(getResources().getColor(R.color.black));
                break;
        }

        switch (myTeam2.color) {
            case "red":
                secondLL.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case "green":
                secondLL.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case "blue":
                secondLL.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case "black":
                secondLL.setBackgroundColor(getResources().getColor(R.color.black));
                break;
        }
    }

    private void setStats() {
        double average;
        String avgString;

        average = ((double) Collections.frequency(myTeam1.values, "correct") /
                ((double) Collections.frequency(myTeam1.values, "correct") + (double) Collections.frequency(myTeam1.values, "incorrect")));

        avgString = String.format("Your average: %d%%", (int) (average * 100));

        String s1 = myTeam1.name + "\n\nPoints: " + Collections.frequency(myTeam1.values, "correct") +
                "\nIncorrect: " + Collections.frequency(myTeam1.values, "incorrect") + "\n\n" + avgString;



        average = ((double) Collections.frequency(myTeam2.values, "correct") /
                ((double) Collections.frequency(myTeam2.values, "correct") + (double) Collections.frequency(myTeam2.values, "incorrect")));

        avgString = String.format("Your average: %d%%", (int) (average * 100));


        String s2 = myTeam2.name + "\n\nPoints: " + Collections.frequency(myTeam2.values, "correct") +
                "\nIncorrect: " + Collections.frequency(myTeam2.values, "incorrect") + "\n\n" + avgString;

        stats1.setText(s1);
        stats2.setText(s2);
    }


/*
    private void setDetailedStats(){
        String tries = String.format("\nNumber of tries: %d \n", correctVal + incorrectVal);
        String speaking = String.format("\n\nCorrect speaks: %d \nIncorrect speaks: %d \n",specorVal, speincVal);
        String drawing = String.format("\nCorrect drawings: %d \nIncorrect drawings: %d \n",dracorVal, draincVal);
        String pantomime = String.format("\nCorrect pantomimes: %d \nIncorrect pantomimes: %d \n",pancorVal, panincVal);

        stats.setText("STATISTICS\n" + tries + speaking + drawing + pantomime);
    }
    */

}