package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_intent);

        Intent intent = getIntent();
        String userData = intent.getStringExtra("userData");

        TextView userDataDisplay = findViewById(R.id.user_data);
        userDataDisplay.setText(userData);
    }
}