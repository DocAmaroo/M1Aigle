package com.example.td1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Ex1Activity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex1);

        // --- Text
        TextView tv = new TextView(this);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setText("Hello, Android");

        // --- Edit Text
        EditText input = new EditText(this);
        input.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        input.setHint("Created Dynamically");
        input.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        // --- MainLayout
        LinearLayout dynamicLayout = findViewById(R.id.dynamic_layout);
        dynamicLayout.addView(tv);
        dynamicLayout.addView(input);

        // Return button
        findViewById(R.id.ex1_return_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}