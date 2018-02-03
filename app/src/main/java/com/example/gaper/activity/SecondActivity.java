package com.example.gaper.activity;

import android.os.CountDownTimer;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends MainActivity {

    private TextView time;
    private Button start;
    private Button stop;
    private Button up;
    private Button down;
    private CountDownTimer countDownTimer;


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
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);


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



}