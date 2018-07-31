package com.example.gaper.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    TextView move1;
    TextView move2;
    TextView w1;
    TextView w2;

    ConstraintLayout firstLL;
    ConstraintLayout secondLL;

    boolean count1 = false;
    boolean count2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        firstLL = (ConstraintLayout) findViewById(R.id.firstLL);
        secondLL = (ConstraintLayout) findViewById(R.id.secondLL);

        stats1 = (TextView) findViewById(R.id.stats1);
        stats2 = (TextView) findViewById(R.id.stats2);
        move1 = (TextView) findViewById(R.id.move1);
        move2 = (TextView) findViewById(R.id.move2);
        w1 = (TextView) findViewById(R.id.w1);
        w2 = (TextView) findViewById(R.id.w2);


        move1.setText("");
        move2.setText("");

        Bundle myBundle = getIntent().getExtras();

        if (myBundle != null) {

            myTeam1 = (Team) myBundle.getSerializable("team1");
            myTeam2 = (Team) myBundle.getSerializable("team2");

            values1 = myTeam1.values;
            values2 = myTeam2.values;


            setBackgroundColor();

            setStats();
        }

        stats2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count1 == false) {
                    setMoveText(2);
                    move2.setVisibility(View.VISIBLE);
                    move2.startAnimation(AnimationUtils.loadAnimation(EndActivity.this, android.R.anim.slide_in_left));
                    w2.startAnimation(AnimationUtils.loadAnimation(EndActivity.this, android.R.anim.slide_out_right));
                    w2.setVisibility(View.INVISIBLE);
                    count1 = true;
                }else {
                    move2.startAnimation(AnimationUtils.loadAnimation(EndActivity.this, android.R.anim.slide_out_right));
                    move2.setVisibility(View.INVISIBLE);
                    w2.setVisibility(View.VISIBLE);
                    w2.startAnimation(AnimationUtils.loadAnimation(EndActivity.this, android.R.anim.slide_in_left));
                    count1 = false;
                }
            }
        });

        stats1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count2 == false) {
                    setMoveText(1);
                    move1.setVisibility(View.VISIBLE);
                    move1.startAnimation(AnimationUtils.loadAnimation(EndActivity.this, android.R.anim.slide_in_left));
                    w1.startAnimation(AnimationUtils.loadAnimation(EndActivity.this, android.R.anim.slide_out_right));
                    w1.setVisibility(View.INVISIBLE);
                    count2 = true;
                }else {
                    move1.startAnimation(AnimationUtils.loadAnimation(EndActivity.this, android.R.anim.slide_out_right));
                    move1.setVisibility(View.INVISIBLE);
                    w1.setVisibility(View.VISIBLE);
                    w1.startAnimation(AnimationUtils.loadAnimation(EndActivity.this, android.R.anim.slide_in_left));
                    count2 = false;
                }
            }
        });

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

        double average1;
        double average2;
        String avgString;

        average1 = ((double) Collections.frequency(myTeam1.values, "correct") /
                ((double) Collections.frequency(myTeam1.values, "correct") + (double) Collections.frequency(myTeam1.values, "incorrect")));

        avgString = String.format("Your average: %d%%", (int) (average1 * 100));

        String s1 = myTeam1.name + "\n\nPoints: " + Collections.frequency(myTeam1.values, "correct") +
                "\nIncorrect: " + Collections.frequency(myTeam1.values, "incorrect") + "\n\n" + avgString;

        SpannableString ss1 = new SpannableString(s1);

        ss1.setSpan(new RelativeSizeSpan(1.5f), 0, myTeam1.name.length(), 0);
        ss1.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, myTeam1.name.length(), 0);
        ss1.setSpan(new StyleSpan(Typeface.ITALIC), myTeam1.name.length() + 2, myTeam1.name.length() + 9, 0);
        ss1.setSpan(new StyleSpan(Typeface.ITALIC), myTeam1.name.length() + 9, myTeam1.name.length() + 21, 0);
        ss1.setSpan(new StyleSpan(Typeface.ITALIC), myTeam1.name.length() + 23, myTeam1.name.length() + 39, 0);
        ss1.setSpan(new RelativeSizeSpan(1.7f), myTeam1.name.length() + 40, s1.length()-1, 0);
        ss1.setSpan(new UnderlineSpan(), myTeam1.name.length() + 40, s1.length()-1, 0);


        average2 = ((double) Collections.frequency(myTeam2.values, "correct") /
                ((double) Collections.frequency(myTeam2.values, "correct") + (double) Collections.frequency(myTeam2.values, "incorrect")));

        avgString = String.format("Your average: %d%%", (int) (average2 * 100));


        String s2 = myTeam2.name + "\n\nPoints: " + Collections.frequency(myTeam2.values, "correct") +
                "\nIncorrect: " + Collections.frequency(myTeam2.values, "incorrect") + "\n\n" + avgString;

        SpannableString ss2 = new SpannableString(s2);

        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, myTeam2.name.length(), 0);
        ss2.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, myTeam2.name.length(), 0);
        ss2.setSpan(new StyleSpan(Typeface.ITALIC), myTeam2.name.length() + 2, myTeam2.name.length() + 9, 0);
        ss2.setSpan(new StyleSpan(Typeface.ITALIC), myTeam2.name.length() + 9, myTeam2.name.length() + 21, 0);
        ss2.setSpan(new StyleSpan(Typeface.ITALIC), myTeam2.name.length() + 23, myTeam2.name.length() + 39, 0);
        ss2.setSpan(new RelativeSizeSpan(1.7f), myTeam2.name.length() + 40, s2.length()-1, 0);
        ss2.setSpan(new UnderlineSpan(), myTeam2.name.length() + 40, s2.length()-1, 0);

        stats1.setText(ss1);
        stats2.setText(ss2);

        if(average1 > average2) {
            w1.setText("You WON! :)");
            w2.setText("You LOST! :(");
        }else if (average1 < average2){
            w1.setText("You LOST! :(");
            w2.setText("You WON! :)");
        }else {
            w1.setText("It's a DRAW");
            w2.setText("It's a DRAW");
        }


    }


    private void setMoveText (int i){
        String text;

        if(i == 1){
            text =  "Correct\n" + "Speaking: " + Collections.frequency(myTeam1.values, "scor") +
                    "\nDrawing: " + Collections.frequency(myTeam1.values, "dcor") +
                    "\nPantomime: " + Collections.frequency(myTeam1.values, "pcor") +
                    "\n\nIncorrect\n" + "Speaking: " + Collections.frequency(myTeam1.values, "sinc") +
                    "\nDrawing: " + Collections.frequency(myTeam1.values, "dinc") +
                    "\nPantomime: " + Collections.frequency(myTeam1.values, "pinc");

            SpannableString sText = new SpannableString(text);

            sText.setSpan(new RelativeSizeSpan(1.2f), text.indexOf("Correct"), text.indexOf("Correct") + 7, 0);
            sText.setSpan(new UnderlineSpan(), text.indexOf("Correct"), text.indexOf("Correct") + 7, 0);
            sText.setSpan(new RelativeSizeSpan(1.2f), text.indexOf("Incorrect"), text.indexOf("Incorrect") + 9, 0);
            sText.setSpan(new UnderlineSpan(), text.indexOf("Incorrect"), text.indexOf("Incorrect") + 9, 0);

            move1.setText(sText);

        }else {
            text =  "Correct\n" + "Speaking: " + Collections.frequency(myTeam2.values, "scor") +
                    "\nDrawing: " + Collections.frequency(myTeam2.values, "dcor") +
                    "\nPantomime: " + Collections.frequency(myTeam2.values, "pcor") +
                    "\n\nIncorrect\n" + "Speaking: " + Collections.frequency(myTeam2.values, "sinc") +
                    "\nDrawing: " + Collections.frequency(myTeam2.values, "dinc") +
                    "\nPantomime: " + Collections.frequency(myTeam2.values, "pinc");

            SpannableString sText = new SpannableString(text);

            sText.setSpan(new RelativeSizeSpan(1.2f), text.indexOf("Correct"), text.indexOf("Correct") + 7, 0);
            sText.setSpan(new UnderlineSpan(), text.indexOf("Correct"), text.indexOf("Correct") + 7, 0);
            sText.setSpan(new RelativeSizeSpan(1.2f), text.indexOf("Incorrect"), text.indexOf("Incorrect") + 9, 0);
            sText.setSpan(new UnderlineSpan(), text.indexOf("Incorrect"), text.indexOf("Incorrect") + 9, 0);

            move2.setText(sText);

        }
    }


}