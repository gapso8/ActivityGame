package com.example.gaper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends SecondActivity {

    private TextView endText;
    private Button stats;

    double correct = 15;
    double incorrect = 8;
    double average = (correct / (correct + incorrect));


    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stats:
                    theEnd();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        stats = (Button) findViewById(R.id.stats);
        stats.setOnClickListener(btnClickListener);
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


    private void theEnd() {
        Intent g = new Intent(EndActivity.this, StatisticsActivity.class);
        startActivity(g);
    }
}

