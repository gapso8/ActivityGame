package com.example.gaper.activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EndActivity extends SecondActivity {

    private TextView endText;

    double average;
    private double incorrectVal;
    private double correctVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        endText = (TextView) findViewById(R.id.endText);

        Bundle myBundle = getIntent().getExtras();
        correctVal = myBundle.getDouble("valueCorrect");
        incorrectVal = myBundle.getDouble("valueIncorrect");

        Log.d("neki", "correct val: " + correctVal);
        Log.d("neki", "incorrect val: " + incorrectVal);

        setText();
    }

    private void setText() {
        average = (correctVal / (correctVal + incorrectVal));

        String avgString = String.format("\nYour average is %d %%", (int)(average * 100));

        if (correctVal + incorrectVal == 0)
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

}

