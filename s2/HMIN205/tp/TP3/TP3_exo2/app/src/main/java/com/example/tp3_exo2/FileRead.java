package com.example.tp3_exo2;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.IOException;

public class FileRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_read);

        TextView userInfo = findViewById(R.id.user_info);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("FILENAME");

        try {
            FileInputStream file = openFileInput(filename);
            int character;
            StringBuilder res = new StringBuilder();
            while ( (character = file.read()) != -1) {
                res.append((char) character);
            }
            userInfo.setText(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}