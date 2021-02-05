package com.example.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Ex4Activity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex4);

        text = findViewById(R.id.hidden_text);
        text.setVisibility(View.INVISIBLE);

        Button simpleToastButton = findViewById(R.id.toaster_button);
        simpleToastButton.setOnClickListener(arg0 -> Toast.makeText(getApplicationContext(), "My toaster", Toast.LENGTH_SHORT).show());

        Button showMessageButton = findViewById(R.id.toggle_secret_message);
        showMessageButton.setOnClickListener(v -> {
            text.setText(R.string.hidden_message);
            if(text.getVisibility() == View.VISIBLE){
                text.setVisibility(View.INVISIBLE);
                showMessageButton.setText(R.string.show_secret_message);
            }else{
                text.setVisibility(View.VISIBLE);
                showMessageButton.setText(R.string.hide_secret_message);
            }
        });

        Button longPressButton = findViewById(R.id.long_press_button);
        longPressButton.setOnLongClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Long click toaster", Toast.LENGTH_LONG).show();
            return true;
        });

        // Return button
        findViewById(R.id.ex4_return_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}