package com.example.tp3_exo5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Planning extends AppCompatActivity {

    private PlanningModel planningModel;
    private TextView slot1;
    private TextView slot2;
    private TextView slot3;
    private TextView slot4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        this.slot1 = findViewById(R.id.slot_1);
        this.slot2 = findViewById(R.id.slot_2);
        this.slot3 = findViewById(R.id.slot_3);
        this.slot4 = findViewById(R.id.slot_4);

        this.planningModel = new ViewModelProvider(this).get(PlanningModel.class);
        planningModel.slot_1.observe(this, s -> slot1.setText(planningModel.getSlot1()));
        planningModel.slot_2.observe(this, s -> slot2.setText(planningModel.getSlot2()));
        planningModel.slot_3.observe(this, s -> slot3.setText(planningModel.getSlot3()));
        planningModel.slot_4.observe(this, s -> slot4.setText(planningModel.getSlot4()));
    }

    public void onNewDay(View v) {
        // Parse planning file
        Intent intent = getIntent();
        String filename = intent.getStringExtra("FILENAME");

        int separator = ';';
        try {
            FileInputStream file = openFileInput(filename);
            int character;
            StringBuilder res = new StringBuilder();
            int i = 0; while ( (character = file.read()) != -1) {
                if ( character == separator) {
                    switch (i) {
                        case 0: planningModel.setSlot1(res.toString());
                        case 1: planningModel.setSlot2(res.toString());
                        case 2: planningModel.setSlot3(res.toString());
                        case 3: planningModel.setSlot4(res.toString());
                    }
                    res = new StringBuilder();
                    i++;
                } else {
                    res.append((char) character);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}