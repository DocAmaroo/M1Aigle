package com.example.availablesensors;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private HashMap<Integer, String> allSensors = new HashMap<>();
    private ArrayList<MySensor> sensors = new ArrayList<>();
    private List<Sensor> systemSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Main sensor list
        //LinearLayout sensorList = (LinearLayout) findViewById(R.id.sensor_container);
        ListView sensorList = findViewById(R.id.sensor_container);


        // Get all available sensors on mobile device
        this.allSensors = getAllSensors();


        // Get user device sensors
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.systemSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);


        // Set our list of sensors
        setSensors();
        sensorList.setAdapter(new SensorAdapter(this, this.sensors));


        findViewById(R.id.btn_accelerometer).setOnClickListener(v -> {
            Intent intent = new Intent(this, Accelerometer.class);
            startActivity(intent);
        });
    }

    public HashMap<Integer, String> getAllSensors() {
        HashMap<Integer, String> allSensors = new HashMap<>();

        allSensors.put(-1, "INVALID");
        allSensors.put(1, "ACCELEROMETER");
        allSensors.put(2, "MAGNETIC_FIELD");
        allSensors.put(3, "ORIENTATION");
        allSensors.put(4, "GYROSCOPE");
        allSensors.put(5, "LIGHT");
        allSensors.put(6, "PRESSURE");
        allSensors.put(7, "TEMPERATURE");
        allSensors.put(8, "PROXIMITY");
        allSensors.put(9, "GRAVITY");
        allSensors.put(10, "LINEAR_ACCELERATION");
        allSensors.put(11, "ROTATION_VECTOR");
        allSensors.put(12, "RELATIVE_HUMIDITY");
        allSensors.put(13, "AMBIENT_TEMPERATURE");
        allSensors.put(14, "MAGNETIC_FIELD_UNCALIBRATED");
        allSensors.put(15, "GAME_ROTATION_VECTOR");
        allSensors.put(16, "GYROSCOPE_UNCALIBRATED");
        allSensors.put(17, "SIGNIFICANT_MOTION");
        allSensors.put(18, "STEP_DETECTOR");
        allSensors.put(19, "STEP_COUNTER");
        allSensors.put(20, "GEOMAGNETIC_ROTATION_VECTOR");
        allSensors.put(21, "HEART_RATE");
        allSensors.put(28, "POSE_6DOF");
        allSensors.put(29, "STATIONARY_DETECT");
        allSensors.put(30, "MOTION_DETECT");
        allSensors.put(31, "HEART_BEAT");
        allSensors.put(33, "ADDITIONAL_INFO");
        allSensors.put(34, "LOW_LATENCY_OFFBODY_DETECT");
        allSensors.put(35, "ACCELEROMETER_UNCALIBRATED");

        return allSensors;
    }


    public void setSensors() {

        ArrayList<Sensor> systemSensorsCpy = new ArrayList<>(this.systemSensors);

        ArrayList<Integer> systemSensorsTypes = new ArrayList<>();
        for (Sensor s : systemSensorsCpy) {
            systemSensorsTypes.add(s.getType());
        }

        System.out.println(systemSensorsTypes);

        for(Map.Entry<Integer, String> entry : allSensors.entrySet()) {
            int index = systemSensorsTypes.indexOf(entry.getKey());
            if (index != -1) {
                MySensor newSensor = new MySensor(systemSensors.get(index).getName(), entry.getValue(), true);
                sensors.add(newSensor);
                systemSensorsCpy.remove(index);
                systemSensorsTypes.remove(index);
            } else {
                MySensor newSensor = new MySensor("UNAVAILABLE", entry.getValue(), false);
                sensors.add(newSensor);
            }
        }

        for (Sensor s : systemSensorsCpy) {
            MySensor newSensor = new MySensor(s.getName(), "UNKNOWN TYPE", true);
            sensors.add(newSensor);
        }
    }
}