package com.example.gaper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    private TextView statitics;
    double correct = 0;
    double incorrect = 0;
    double average = (correct / (correct + incorrect));
    double pancor = 0;
    double paninc = 0;
    double specor = 0;
    double speinc = 0;
    double dracor = 0;
    double drainc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        statitics = (TextView) findViewById(R.id.statistics);
    }
}
