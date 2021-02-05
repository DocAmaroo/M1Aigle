package com.example.availablesensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get device sensors
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        LinearLayout sensorList = (LinearLayout) findViewById(R.id.sensor_container);

        for (Sensor s : sensors) {
            TextView newSensor = new TextView(this);
            newSensor.setText(s.getName());
            newSensor.setGravity(Gravity.CENTER);

            sensorList.addView(newSensor);
        }

        //https://developer.android.com/guide/topics/sensors/sensors_overview
    }
}