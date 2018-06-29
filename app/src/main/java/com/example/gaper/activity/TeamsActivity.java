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
    private Button done;
    private EditText nameInput;

    private ImageButton red;
    private ImageButton green;
    private ImageButton blue;
    private ImageButton black;

    private float scale;

    ImageButton[] buttons;

    String name = "";
    String color = "";
    String[] teamName = new String[2];
    String[] teamColor = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        done = (Button) findViewById(R.id.done);
        text = (TextView) findViewById(R.id.text);
        nameInput = (EditText) findViewById(R.id.nameInput);

        text.setText("1. Team");
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

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameInput.getText().toString().equals("") || color == ""){
                    Toast.makeText(TeamsActivity.this, "Enter required information!",
                            Toast.LENGTH_LONG).show();
                }else {
                    if (text.getText().equals("1. Team")) {
                        setInfo(0);
                        nameInput.setText("2. Team");
                    } else {
                        if (nameInput.getText().toString().equals(teamName[0]) || color == teamColor[0]) {
                            Toast.makeText(TeamsActivity.this, "Team names and/or team colors shouldn't match",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            setInfo(1);
                            Bundle myBund = new Bundle();
                            myBund.putStringArray("teamName", teamName);
                            myBund.putStringArray("teamColor", teamColor);
                            Intent i = new Intent(TeamsActivity.this, PlayActivity.class);
                            i.putExtras(myBund);
                            startActivity(i);
                        }
                    }
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
        name = nameInput.getText().toString();
        teamName[team] = name;
        teamColor[team] = color;
        color = "";
        name = "";
        setButtonScale(buttons, "");
    }


}
