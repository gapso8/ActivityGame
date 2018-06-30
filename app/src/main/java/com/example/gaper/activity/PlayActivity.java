package com.example.gaper.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.gaper.activity.database.MyDatabase;
import com.example.gaper.activity.database.Words;


public class PlayActivity extends MainActivity {

    private TextView playingTeamName;
    private TextView time;
    private TextView share;
    private TextView word;
    private Button start;
    private Button stop;
    private Button up;
    private Button down;
    private Button newWord;
    private Button end;
    private CountDownTimer countDownTimer;
    private String[] presentation = {"speaking", "drawing", "pantomime"};
    private String a = "";
    public MediaPlayer mp;

    private int correct, incorrect, specor, speinc, dracor, drainc, pancor, paninc;
    private MyDatabase myDB = new MyDatabase(this);

    private String [] teamName;
    private String [] teamColor;
    private String [][] players;

    int RED = getResources().getColor(R.color.red);
    int GREEN = getResources().getColor(R.color.green);
    int BLUE = getResources().getColor(R.color.blue);
    int BLACK = getResources().getColor(R.color.black);

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start:
                    start(90);
                    time.setTextColor(Color.parseColor("#000000"));
                    enableButtons(true, 4);
                    break;
                case R.id.stop:
                    cancel();
                    enableButtons(true, 2);
                    break;
                case R.id.newWord:
                    setRandomWord();
                    time.setText("");
                    enableButtons(false, 1);
                    break;
                case R.id.up:
                    enableButtons(true, 3);
                    points(true);
                    break;
                case R.id.down:
                    enableButtons(true, 3);
                    points(false);
                    break;
                case R.id.word:
                    goTo(v);
                    break;
                case R.id.end:
                    theEnd();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        playingTeamName = (TextView) findViewById(R.id.teamName);
        playingTeamName.setText(teamName[0]);

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(btnClickListener);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(btnClickListener);
        up =  (Button)findViewById(R.id.up);
        up.setOnClickListener(btnClickListener);
        down = (Button)findViewById(R.id.down);
        down.setOnClickListener(btnClickListener);
        newWord = (Button)findViewById(R.id.newWord);
        newWord.setOnClickListener(btnClickListener);
        word = (TextView) findViewById(R.id.word);
        word.setOnClickListener(btnClickListener);
        end = (Button) findViewById(R.id.end);
        end.setOnClickListener(btnClickListener);

        start.setEnabled(false);
        stop.setEnabled(false);

        share = (TextView) findViewById(R.id.share);
        time = (TextView) findViewById(R.id.time);

        word.setText(null);
        share.setText(null);
        time.setText(null);


        mp  = MediaPlayer.create(this, R.raw.sound);

        //populateDatabase();

        Bundle myBund = getIntent().getExtras();
        teamName = myBund.getStringArray("teamName");
        teamColor = myBund.getStringArray("teamColor");
        players = (String [][]) myBund.getSerializable("players");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void start(int timeS) {
        time.setText(null);
        countDownTimer = new CountDownTimer(timeS * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int sek = (int)millisUntilFinished / 1000;
                int minute = sek / 60;
                int preostaleSekunde = sek - minute * 60;

                String secondString = preostaleSekunde < 2 ? " second left" : " seconds left";

                if (minute < 1) {
                    time.setText(preostaleSekunde +  secondString);
                }
                else {
                    time.setText(minute + " minute : " + preostaleSekunde + secondString);
                }
                if(sek <= 10){
                    time.setTextColor(Color.parseColor("#FF0000"));
                    mp.start();
                }
            }
            @Override
            public void onFinish() {
                time.setText("Time's up!");
                mp.start();
                mp.release();
                cancel();
                enableButtons(true, 3);
                stop.setEnabled(false);
                points(false);
                vibrateOnEnd();
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

    private void enableButtons(boolean enable, int a){
        if (enable) {
            switch (a) {
                case 1:
                    up.setEnabled(true);
                    down.setEnabled(true);
                    newWord.setEnabled(true);
                    break;
                case 2:
                    up.setEnabled(true);
                    down.setEnabled(true);
                    stop.setEnabled(false);
                    break;
                case 3:
                    newWord.setEnabled(true);
                    up.setEnabled(false);
                    down.setEnabled(false);
                    break;
                case 4:
                    start.setEnabled(false);
                    stop.setEnabled(true);
                    break;
            }
        }
        else {
            up.setEnabled(false);
            down.setEnabled(false);
            newWord.setEnabled(false);
            stop.setEnabled(false);
            start.setEnabled(true);
        }
    }

    private void setRandomWord() {
        Cursor myCursor = myDB.getWordFromTable((int)(Math.random() * Words.getWords().length-1));

        if (myCursor.moveToFirst()){
                String data = myCursor.getString(myCursor.getColumnIndex("word"));
                word.setText(data);
                share.setText(presentation[(int) (Math.random() * presentation.length)]);
        }
        myCursor.close();
    }

    public void goTo(View view) {
        a = word.getText().toString();
        goToUrl("http://www.dictionary.com/browse/"+ a +"?s=t");
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    private void theEnd(){
        Bundle myBund = new Bundle();
        myBund.putInt("valueCorrect",  correct);
        myBund.putInt("valueIncorrect", incorrect);
        myBund.putInt("valueSpecor", specor);
        myBund.putInt("valueSpeinc", speinc);
        myBund.putInt("valueDracor", dracor);
        myBund.putInt("valueDrainc", drainc);
        myBund.putInt("valuePancor", pancor);
        myBund.putInt("valuePaninc", paninc);
        correct = 0; incorrect = 0;
        specor = 0; speinc = 0;
        dracor = 0; drainc = 0;
        pancor = 0; paninc = 0;
        up.setText("Correct"); down.setText("Incorrect");
        Intent j = new Intent(getApplicationContext(), EndActivity.class);
        j.putExtras(myBund);
        startActivity(j);
    }

    private void points(boolean a){
        if(a) {
            correct++;
            up.setText("Correct\n(" + correct + ")");
            switch(share.getText().toString()){
                case "speaking":
                    specor++;
                    break;
                case "drawing":
                    dracor++;
                    break;
                case "pantomime":
                    pancor++;
                    break;
            }
        }else {
            incorrect++;
            down.setText("Incorrect\n(" + incorrect + ")");
            switch(share.getText().toString()){
                case "speaking":
                    speinc++;
                    break;
                case "drawing":
                    drainc++;
                    break;
                case "pantomime":
                    paninc++;
                    break;
            }
        }
    }

    private void vibrateOnEnd() {
        Vibrator myVib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (myVib.hasVibrator()) {
            myVib.vibrate(1000);
        }
    }


    private void populateDatabase() {
        myDB.clear();
        myDB.insertScore();
    }

}