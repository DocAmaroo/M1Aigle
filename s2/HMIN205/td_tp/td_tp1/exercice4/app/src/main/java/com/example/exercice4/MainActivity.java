package com.example.exercice4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button simpleToastButton;
    private Button showMessageButton;
    private Button longPressButton;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.hidden_text);
        text.setVisibility(View.INVISIBLE);

        simpleToastButton = (Button) findViewById(R.id.toaster_button);
        simpleToastButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "My toaster", Toast.LENGTH_SHORT).show();
            }
        });

        showMessageButton = (Button) findViewById(R.id.show_hidden_message_btn);
        showMessageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                text.setText(R.string.hidden_message);
                if(text.getVisibility() == View.VISIBLE){
                    text.setVisibility(View.INVISIBLE);
                }else{
                    text.setVisibility(View.VISIBLE);
                }
            }
        });

        longPressButton = (Button) findViewById(R.id.long_press_button);
        longPressButton.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "Long click toaster", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
}