package com.example.availablesensors;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TableRow;
import android.widget.TextView;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Accelerometer extends AppCompatActivity implements SensorEventListener {


    private static final float SHAKE_THRESHOLD = 2.5F;


    // --- Sensors
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private float[] lastPos = new float[3];
    private String[] direction = {"NONE", "NONE"};
    private long lastUpdate;
    StringBuilder builder = new StringBuilder();


    // --- Camera
    private CameraManager camManager;
    private String cameraId;
    private boolean flashState;


    // --- Views
    private TableRow tableX, tableY, tableZ;
    private TextView TextPosX, TextPosY, TextPosZ, TextDirection, TextFlashlight;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        // --- (We should ask first for permission but anyway :))
        camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = camManager.getCameraIdList()[0];
            flashState = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


        // --- Get all tables rows
        tableX = findViewById(R.id.layout_position_x);
        tableY = findViewById(R.id.layout_position_y);
        tableZ = findViewById(R.id.layout_position_z);


        // --- Get all text views
        TextPosX = findViewById(R.id.txt_position_x);
        TextPosY = findViewById(R.id.txt_position_y);
        TextPosZ = findViewById(R.id.txt_position_z);
        TextDirection = findViewById(R.id.txt_direction);
        TextFlashlight = findViewById(R.id.txt_flashlight);

        
        // --- Listen the accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // --- Next activity button
        findViewById(R.id.btn_proximity).setOnClickListener(v -> {
            Intent intent = new Intent(this, Proximity.class);
            startActivity(intent);
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); // unregister sensor
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;
        float currentPosX, currentPosY, currentPosZ;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            currentPosX = event.values[0];
            currentPosY = event.values[1];
            currentPosZ = event.values[2];

            // --- Refresh every 250ms
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 250) {
                lastUpdate = curTime;
                toggleBackground(currentPosX, currentPosY, currentPosZ);
                setPosition(currentPosX, currentPosY, currentPosZ);
                setDirection(currentPosX, currentPosY);
            }

            try {
                toggleFlash(currentPosX, currentPosY, currentPosZ);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            lastPos[0] = currentPosX;
            lastPos[1] = currentPosY;
            lastPos[2] = currentPosZ;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public int getNewBgColor(float val) {
        if (val <= -1) {
            return getResources().getColor(R.color.lightgreen);
        } else if (val > -1 && val < 1) {
            return getResources().getColor(R.color.black);
        } else {
            return getResources().getColor(R.color.lightred);
        }
    }


    public void setPosition(float x, float y, float z) {
        TextPosX.setText(String.valueOf(x));
        TextPosY.setText(String.valueOf(y));
        TextPosZ.setText(String.valueOf(z));
    }


    public void setDirection(float x, float y) {
        float xChange = lastPos[0] - x;
        float yChange = lastPos[1] - y;

        if (xChange > 2) {
            direction[0] = getResources().getString(R.string.direction_left);
        } else if (xChange < -2) {
            direction[0] = getResources().getString(R.string.direction_right);
        }

        if (yChange > 2) {
            direction[1] = getResources().getString(R.string.direction_down);
        } else if (yChange < -2) {
            direction[1] = getResources().getString(R.string.direction_up);
        }

        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        builder.append(" y: ");
        builder.append(direction[1]);

        TextDirection.setText(builder.toString());
    }


    public void toggleBackground(float x, float y, float z) {
        tableX.setBackgroundColor(getNewBgColor(x));
        tableY.setBackgroundColor(getNewBgColor(y));
        tableZ.setBackgroundColor(getNewBgColor(z));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void toggleFlash(float x, float y, float z) throws CameraAccessException {
        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        if (gForce > SHAKE_THRESHOLD) {
            if (flashState) {
                camManager.setTorchMode(cameraId, false);
                flashState = false;
                TextFlashlight.setText(getResources().getText(R.string.txt_flashlight_on));
            } else {
                camManager.setTorchMode(cameraId, true);
                flashState = true;
                TextFlashlight.setText(getResources().getText(R.string.txt_flashlight_off));
            }
        }
    }
}