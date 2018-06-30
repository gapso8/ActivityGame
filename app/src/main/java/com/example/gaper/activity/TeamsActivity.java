package com.example.gaper.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TeamsActivity extends MainActivity {

    private TextView text;
    private EditText nameInput;

    private ImageButton red;
    private ImageButton green;
    private ImageButton blue;
    private ImageButton black;

    private EditText player1Input;
    private EditText player2Input;

    private Button done;

    private float scale;

    ImageButton[] buttons;

    //String name = "";
    String color = "";
    String[] teamName = new String[2];
    String[] teamColor = new String[2];
    String[][] players = new String[2][2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        text = (TextView) findViewById(R.id.text);
        nameInput = (EditText) findViewById(R.id.nameInput);
        nameInput.setText(text.getText().toString());

        buttons = new ImageButton[]{
                red = (ImageButton) findViewById(R.id.red),
                green = (ImageButton) findViewById(R.id.green),
                blue = (ImageButton) findViewById(R.id.blue),
                black = (ImageButton) findViewById(R.id.black)
        };

        for (ImageButton btn : buttons) {
            btn.setOnClickListener(btnClickListener);
        }

        player1Input = (EditText) findViewById(R.id.player1Input);
        player2Input = (EditText) findViewById(R.id.player2Input);

        done = (Button) findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (text.getText().equals("1. Team")) {
                        if(checkInfo())
                            setInfo(0);
                    } else if (checkInfo()){
                            setInfo(1);
                            Bundle myBund = new Bundle();
                            myBund.putStringArray("teamName", teamName);
                            myBund.putStringArray("teamColor", teamColor);
                            myBund.putSerializable("players", players);
                            Intent i = new Intent(TeamsActivity.this, PlayActivity.class);
                            i.putExtras(myBund);
                            startActivity(i);
                    }
            }
        });
    }

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.red:
                    setButtonScale(buttons, "red");
                    break;
                case R.id.green:
                    setButtonScale(buttons, "green");
                    break;

                case R.id.blue:
                    setButtonScale(buttons, "blue");
                    break;

                case R.id.black:
                    setButtonScale(buttons, "black");
                    break;
            }
        }
    };

    private void setButtonScale(ImageButton[] buttons, String a) {
        for (ImageButton btn : buttons) {
            if (a.equals(btn.getResources().getResourceName(btn.getId()).substring(30)))
                scale = 1.5f;
            else
                scale = 1;

            btn.setScaleX(scale);
            btn.setScaleY(scale);

            color = a;
        }
    }

    private void setInfo(int team) {
        text.setText("2. Team");
        teamName[team] = nameInput.getText().toString();
        teamColor[team] = color;
        players[team][0] = player1Input.getText().toString();
        players[team][1] = player2Input.getText().toString();
        color = "";
        nameInput.setText("2. Team");
        player1Input.setText("Player 1");
        player2Input.setText("Player 2");
        setButtonScale(buttons, "");
    }

    private boolean checkInfo() {
        boolean validInfo = false;
        if(nameInput.getText().toString().equals("") || color.equals("") ||
                player1Input.getText().toString().equals("") || player2Input.getText().toString().equals("")){
            Toast.makeText(TeamsActivity.this, "Enter required information!",
                    Toast.LENGTH_LONG).show();
        }else if(player1Input.getText().toString().equals(player2Input.getText().toString())){
            Toast.makeText(TeamsActivity.this, "Names of the players have to be different!",
                    Toast.LENGTH_LONG).show();
        }else if (text.getText().equals("2. Team") && nameInput.getText().toString().equals(teamName[0]) || color.equals(teamColor[0])) {
            Toast.makeText(TeamsActivity.this, "Team names and/or team colors shouldn't match",
                    Toast.LENGTH_LONG).show();
        }else
            validInfo = true;

        return validInfo;
    }
}
