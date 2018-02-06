package com.example.gaper.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SecondActivity extends MainActivity {

    private TextView time;
    private TextView Word;
    private Button start;
    private Button stop;
    private Button up;
    private Button down;
    private Button newWord;
    private CountDownTimer countDownTimer;

    private String[] words;

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start:
                    enableButtons(false);
                    start(90);
                    break;
                case R.id.stop:
                    cancel();
                    enableButtons(true);
                    break;
                case R.id.newWord:
                    setRandomWord();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(btnClickListener);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(btnClickListener);
        time = (TextView) findViewById(R.id.time);
        up =  (Button)findViewById(R.id.up);
        down = (Button)findViewById(R.id.down);
        newWord = (Button)findViewById(R.id.newWord);
        newWord.setOnClickListener(btnClickListener);
        Word = (TextView) findViewById(R.id.Word);

        createWordsList();
        setRandomWord();
    }

    public void start(int timeS) {
        time.setText("15");

        countDownTimer = new CountDownTimer(timeS * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);

                int sek = (int)millisUntilFinished / 1000;
                int minute = sek / 60;
                int preoStaleSekunde = sek - minute * 60;
                time.setText("" + minute + " : " + preoStaleSekunde);
            }
            @Override
            public void onFinish() {
                time.setText("Done!!!");
            }
        };

        countDownTimer.start();

    }

    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            time.setText(null);
        }
    }

    private void enableButtons(boolean enable){
        if (enable) {
            up.setEnabled(true);
            down.setEnabled(true);
        }
        else {
            up.setEnabled(false);
            down.setEnabled(false);
        }
    }

    private void createWordsList() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getAssets().open("asset/random_words.txt")));
            String line = in.readLine();
            words = line.split(" ");
        } catch (IOException e) {
            System.out.print("Napaka pri branju datoteke: " + e.getMessage());
        }
    }

    private void setRandomWord () {
        Word.setText(words[(int)(Math.random() * 998) + 1]);
    }
}