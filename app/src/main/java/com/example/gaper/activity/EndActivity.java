package com.example.gaper.activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EndActivity extends SecondActivity {

    private TextView endText;
    private TextView stats;

    double average;
    private int incorrectVal;
    private int correctVal;
    private double correct;
    private double incorrect;
    private int specorVal;
    private int speincVal;
    private int dracorVal;
    private int draincVal;
    private int pancorVal;
    private int panincVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        endText = (TextView) findViewById(R.id.endText);
        stats = (TextView) findViewById(R.id.stats);

        Bundle myBundle = getIntent().getExtras();
        correctVal = myBundle.getInt("valueCorrect"); correct = correctVal;
        incorrectVal = myBundle.getInt("valueIncorrect"); incorrect = incorrectVal;
        specorVal = myBundle.getInt("valueSpecor");
        speincVal = myBundle.getInt("valueSpeinc");
        dracorVal = myBundle.getInt("valueDracor");
        draincVal = myBundle.getInt("valueDrainc");
        pancorVal = myBundle.getInt("valuePancor");
        panincVal = myBundle.getInt("valuePaninc");

        setText();
        setStats();
    }

    private void setText() {
        average = (correct / (correct + incorrect));
        String avgString = String.format("\nYour average is %d %%", (int)(average * 100));

        if (correct + incorrect == 0)
            endText.setText("You have to do at least one word");
        else if (average >= 0.9)
            endText.setText("Very good!" + avgString);
        else if (average >= 0.7 && average < 0.9)
            endText.setText("It could be better!" + avgString);
        else if (average >= 0.5 && average < 0.7)
            endText.setText("You definitely need more practise!" + avgString);
        else if (average >= 0.0 && average < 0.5)
            endText.setText("Very bad!" + avgString);
    }

    private void setStats(){
        String tries = String.format("\nNumber of tries: %d \n", correctVal + incorrectVal);
        String speaking = String.format("\n\nCorrect speaks: %d \nIncorrect speaks: %d \n",specorVal, speincVal);
        String drawing = String.format("\nCorrect drawings: %d \nIncorrect drawings: %d \n",dracorVal, draincVal);
        String pantomime = String.format("\nCorrect pantomimes: %d \nIncorrect pantomimes: %d \n",pancorVal, panincVal);

        stats.setText("STATISTICS\n" + tries + speaking + drawing + pantomime);
    }


}

