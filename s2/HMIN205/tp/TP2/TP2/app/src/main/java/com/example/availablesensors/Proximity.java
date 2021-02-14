package com.example.availablesensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Proximity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorProximity;
    private TextView textDistance;

    private ImageView catholic;
    private ImageView rick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        textDistance = findViewById(R.id.txt_proximity_distance_value);
        rick = findViewById(R.id.img_rick);
        catholic = findViewById(R.id.img_catholic);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (sensorProximity == null) {
            textDistance.setText("No Proximity Sensor!");
        } else {
            sensorManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); // unregister sensor
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        String distanceToString = String.valueOf(distance);
        textDistance.setText(distanceToString);

        if (distance == 0) {
            rick.setVisibility(View.VISIBLE);
            catholic.setVisibility(View.INVISIBLE);
        } else {
            rick.setVisibility(View.INVISIBLE);
            catholic.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}