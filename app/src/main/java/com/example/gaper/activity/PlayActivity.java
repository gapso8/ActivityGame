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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.gaper.activity.database.MyDatabase;
import com.example.gaper.activity.database.Words;
import java.util.ArrayList;
import java.util.Collections;


public class PlayActivity extends MainActivity {

    private TextView playingTeamName;
    private TextView playerOn;
    private ImageButton visible;
    private Button start;
    private Button stop;
    private TextView time;
    private TextView word;
    private TextView share;
    private Button newWord;
    private Button up;
    private Button down;
    private TextView points1Team;
    private TextView points2Team;
    private TextView points1;
    private TextView points2;
    private Button end;

    private String a = "";
    private String p = "points";

    private String[] presentation = {"speaking", "drawing", "pantomime"};
    private String [] teamName;
    private String [] teamColor;
    private int [] teamColorInt = new int [2];
    private String [][] players;
    private ArrayList values1 = new ArrayList();
    private ArrayList values2 = new ArrayList();

    private Bundle myBund = null;
    public MediaPlayer mp;
    private MyDatabase myDB = new MyDatabase(this);
    private CountDownTimer countDownTimer;
/*
    int RED = getResources().getColor(R.color.red);
    int GREEN = getResources().getColor(R.color.green);
    int BLUE = getResources().getColor(R.color.blue);
    int BLACK = getResources().getColor(R.color.black);
*/
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start:
                    start(90);
                    word.setVisibility(View.INVISIBLE);
                    visible.setVisibility(View.VISIBLE);
                    time.setTextColor(Color.parseColor("#000000"));
                    enableButtons(true, 4);
                    break;
                case R.id.stop:
                    cancel();
                    word.setVisibility(View.VISIBLE);
                    visible.setVisibility(View.INVISIBLE);
                    enableButtons(true, 2);
                    break;
                case R.id.newWord:
                    changeNames();
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

        //populateDatabase();

        //ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        //constraintLayout.setBackgroundColor(getResources().getColor(R.color.red));

        playingTeamName = (TextView) findViewById(R.id.teamName);
        playerOn = (TextView) findViewById(R.id.playerOn);
        visible = (ImageButton) findViewById(R.id.visible);

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

        share = (TextView) findViewById(R.id.share);
        time = (TextView) findViewById(R.id.time);
        points1 = (TextView) findViewById(R.id.points1);
        points2 = (TextView) findViewById(R.id.points2);
        points1Team = (TextView) findViewById(R.id.points1Team);
        points2Team = (TextView) findViewById(R.id.points2Team);

        word.setText(null);
        share.setText(null);
        time.setText(null);

        visible.setOnClickListener(btnClickListener);
        visible.setVisibility(View.INVISIBLE);

        mp = MediaPlayer.create(this, R.raw.sound);

        myBund = getIntent().getExtras();

        if (myBund != null) {

            Log.d("neki", myBund + " my bundle ");

            teamName = myBund.getStringArray("teamName");   //Error
            teamColor = myBund.getStringArray("teamColor");
            players = (String[][]) myBund.getSerializable("players");

            playingTeamName.setText(teamName[0]);
            playerOn.setText(players[0][0]);
            points1Team.setText(teamName[0]);
            points2Team.setText(teamName[1]);

            start.setEnabled(false);
            stop.setEnabled(false);

            //setTeamColor();

            visible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    word.setVisibility(View.INVISIBLE);
                }
            });

            visible.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    word.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }
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

    private void points(boolean a){
        if(a) {
            setValues("correct");
            points1.setText(getTeamPoints(values1) + " " + p);
            points2.setText(getTeamPoints(values2) + " " + p);

            switch(share.getText().toString()){
                case "speaking":
                    setValues("scor");
                    break;

                case "drawing":
                    setValues("dcor");
                    break;

                case "pantomime":
                    setValues("pcor");
                    break;
            }
        }else {
            setValues("incorrect");
            points1.setText(getTeamPoints(values1) + " " + p);
            points2.setText(getTeamPoints(values2) + " " + p);

            switch(share.getText().toString()){
                case "speaking":
                    setValues("sinc");
                    break;

                case "drawing":
                   setValues("dinc");
                    break;

                case "pantomime":
                    setValues("pinc");
                    break;
            }
        }
    }

    private void setValues(String val){
        if(playingTeamName.getText().toString().equals(teamName[0])){
            values1.add(val);
        }else{
            values2.add(val);
        }
    }

    private int getTeamPoints(ArrayList val){
        int points = 0;
        points = Collections.frequency(val, "correct");
        if (points == 1)
            p = "point";
        else
            p = "points";
        return points;
    }

    private void theEnd(){
        Bundle myBund = new Bundle();
        Team team1 = new Team(teamName[0], teamColorInt[0], players[0][0], players[0][1], values1);
        Team team2 = new Team(teamName[1], teamColorInt[1], players[1][0], players[1][1], values2);

        Intent j = new Intent(getApplicationContext(), EndActivity.class);
        j.putExtra("team1", team1);
        j.putExtra("team2", team2);
        startActivity(j);
        finish();
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

    private void changeNames(){
        String pt = playingTeamName.getText().toString();
        String p = playerOn.getText().toString();

        if(pt.equals(teamName[0]) && word.getText().toString().equals("")) {
            playingTeamName.setText(teamName[0]);
            playerOn.setText(players[0][0]);

        }else {
            if(pt.equals(teamName[0])){
                playingTeamName.setText(teamName[1]);


                if(p.equals(players[0][0])) {
                    playerOn.setText(players[1][0]);
                }else if(p.equals(players[0][1]))
                    playerOn.setText(players[1][1]);
                else if(p.equals(players[1][1]))
                    playerOn.setText(players[0][0]);

            }else {
                playingTeamName.setText(teamName[0]);

                if (p.equals(players[1][0]))
                    playerOn.setText(players[0][1]);
                else if(p.equals(players[1][1]))
                    playerOn.setText(players[0][0]);
                else if(p.equals(players[0][1]))
                    playerOn.setText(players[1][1]);
            }
        }
    }

    /*private void setTeamColor(){
        String color = teamColor[0];

        for(int i= 0; i < 2; i++) {
            switch (color) {
                case "red":
                    teamColorInt[i] = RED;
                    break;
                case "green":
                    teamColorInt[i] = GREEN;
                    break;
                case "blue":
                    teamColorInt[i] = BLUE;
                    break;
                case "black":
                    teamColorInt[i] = BLACK;
                    break;
            }
            color = teamColor[1];
        }
    }
*/
}

