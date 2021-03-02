package com.example.tp3_exo6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;

public class FileRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_read);

        Intent intent = getIntent();

        // Get the filename
        String filename = intent.getStringExtra("FILENAME");
        try {
            FileInputStream file = openFileInput(filename);
            int character;
            StringBuilder res = new StringBuilder();
            while ( (character = file.read()) != -1) {
                res.append((char) character);
            }
            TextView userInfo = findViewById(R.id.user_info);
            userInfo.setText(res);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get utilisation counter
        int utilisationCounter = intent.getIntExtra("UTILISATION", -1);
        if (utilisationCounter != -1) {
            TextView onResumeCounter = findViewById(R.id.on_resume_counter);
            onResumeCounter.setText(String.valueOf(utilisationCounter));
        }
    }
}