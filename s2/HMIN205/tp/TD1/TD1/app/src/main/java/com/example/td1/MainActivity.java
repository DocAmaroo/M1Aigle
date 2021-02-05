package com.example.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ex1_btn).setOnClickListener(v -> { openActivity(1); });
        findViewById(R.id.ex2_btn).setOnClickListener(v -> { openActivity(2); });
        findViewById(R.id.ex3_btn).setOnClickListener(v -> { openActivity(3); });
        findViewById(R.id.ex4_btn).setOnClickListener(v -> { openActivity(4); });
        findViewById(R.id.ex5_btn).setOnClickListener(v -> { openActivity(5); });
    }

    private void openActivity(int number) {
        Intent intent = null;
        if (number == 1) {
            intent = new Intent(this, Ex1Activity.class);
        } else if (number == 2) {
            intent = new Intent(this, Ex2Activity.class);
        } else if (number == 3) {
            intent = new Intent(this, Ex3Activity.class);
        } else if (number == 4){
            intent = new Intent(this, Ex4Activity.class);
        } else {
            intent = new Intent(this, Ex5Activity.class);
        }
        startActivity(intent);
    }
}