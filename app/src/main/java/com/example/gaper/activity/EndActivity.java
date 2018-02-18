package com.example.gaper.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EndActivity extends SecondActivity {

    private TextView endText;

    //tuki bi hotu nardit tko da bi passal double value correct v ta activity
    //Bundle extras = getIntent().getExtras();

    //double correct = extras.getDouble("valueCorrect");
    //double Incorrect = extras.getDouble("valueIncorrect");
    double correct = 1;
    double incorrect = 1;
    double average = (correct / (correct + incorrect));




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        endText = (TextView) findViewById(R.id.endText);

        setText(average);
    }

    private void setText(double average) {
        if (correct + incorrect == 0)
            endText.setText("You have to do at least one word");
        else if (average >= 0.9)
            endText.setText("Very good!\nYour average is " + average * 100 + "%");
        else if (average >= 0.7 && average < 0.9)
            endText.setText("It could be better!\nYour average is " + average * 100 + "%");
        else if (average >= 0.5 && average < 0.7)
            endText.setText("You definitely need more practise!\nYour average is " + average * 100 + "%");
        else if (average >= 0.0 && average < 0.5)
            endText.setText("Very bad!\nYour average is " + average * 100 + "%");
    }

}

