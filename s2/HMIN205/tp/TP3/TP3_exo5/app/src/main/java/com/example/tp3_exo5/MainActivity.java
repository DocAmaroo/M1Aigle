package com.example.tp3_exo5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String userId;
    private Utilisation utilisation;
    private TextView userIdText;
    private EditText userNameEntry;
    private EditText userFirstNameEntry;
    private EditText userPhoneEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // LyfeCycleAware
        this.utilisation = new Utilisation(this);
        getLifecycle().addObserver(this.utilisation);

        this.userIdText = findViewById(R.id.user_id);
        this.userNameEntry = findViewById(R.id.user_name_entry);
        this.userFirstNameEntry = findViewById(R.id.user_firstname_entry);
        this.userPhoneEntry = findViewById(R.id.user_phone_entry);

        if (savedInstanceState == null) {
            generateRandomId();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save user id
        savedInstanceState.putString("USER_ID", this.userId);

        // Get all entries
        String username = this.userNameEntry.getText().toString();
        String firstname = this.userFirstNameEntry.getText().toString();
        String phone = this.userPhoneEntry.getText().toString();
        ArrayList<String> entries = new ArrayList<>(Arrays.asList(username, firstname, phone));

        // saved them
        savedInstanceState.putStringArrayList("USER_ENTRIES", entries);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {

            // restore user id
            if (savedInstanceState.containsKey("USER_ID")) {
                this.userId = savedInstanceState.getString("USER_ID");
                this.userIdText.setText(this.userId);
            }

            // restore entries
            if (savedInstanceState.containsKey("USER_ENTRIES")) {
                ArrayList<String> entries = savedInstanceState.getStringArrayList("USER_ENTRIES");
                this.userNameEntry.setText(entries.get(0));
                this.userFirstNameEntry.setText(entries.get(1));
                this.userPhoneEntry.setText(entries.get(2));
            }
        }
    }

    private void generateRandomId() {
        long number = Math.round((Math.random() * 10000));
        this.userId = "i" + number;
        this.userIdText.setText(userId);
    }

    public void onSubmit(View v) {
        String newLine = System.getProperty("line.separator");
        String filename = this.userNameEntry.getText().toString() + "_" + this.userId;
        try {
            FileOutputStream file = openFileOutput(filename, Context.MODE_PRIVATE);
            String toWrite = "ID" + ":" + this.userId;
            file.write(toWrite.getBytes());
            file.write(newLine.getBytes());
            toWrite = getResources().getString(R.string.label_name).toUpperCase() + ":" + this.userNameEntry.getText().toString();
            file.write(toWrite.getBytes());
            file.write(newLine.getBytes());
            toWrite = getResources().getString(R.string.label_firstname).toUpperCase() + ":" + this.userFirstNameEntry.getText().toString();
            file.write(toWrite.getBytes());
            file.write(newLine.getBytes());
            toWrite = getResources().getString(R.string.label_phone_number).toUpperCase() + ":" + this.userPhoneEntry.getText().toString();
            file.write(toWrite.getBytes());
            file.write(newLine.getBytes());
            file.write(utilisation.toString().getBytes());
            file.close();
            Toast.makeText(getApplicationContext(), R.string.submit_success, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), R.string.submit_error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        Intent newActivity = new Intent(this, FileRead.class);
        newActivity.putExtra("FILENAME", filename);
        this.startActivity(newActivity);
    }

    public void onShowPlanning(View v) {
        String newLine = System.getProperty("line.separator");
        String filename = "planning";
        try {
            FileOutputStream file = openFileOutput(filename, Context.MODE_PRIVATE);
            String toWrite = "Grasse matinee;Apero;Sieste;Petanque;";
            file.write(toWrite.getBytes());
            file.write(newLine.getBytes());
            file.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), R.string.submit_error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        Intent planning = new Intent(this, Planning.class);
        planning.putExtra("FILENAME", filename);
        this.startActivity(planning);
    }
}