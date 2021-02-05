package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyIntent extends AppCompatActivity {

    private String name;
    private String firstname;
    private String birthday;
    private String phonenumber;
    private String foe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_intent);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        // Retrieve data
        this.name = intent.getStringExtra("userName");
        this.firstname = intent.getStringExtra("userFirstName");
        this.birthday = intent.getStringExtra("userBirhday");
        this.phonenumber = intent.getStringExtra("userPhoneNumber");
        this.foe = intent.getStringExtra("userFieldOfExp");

        TextView userDataDisplay = findViewById(R.id.user_data);
        userDataDisplay.setText(userDataToString());

        findViewById(R.id.ok_button).setOnClickListener(v -> {
            Intent okIntent = new Intent(this, OkActivity.class);
            okIntent.putExtra("userPhoneNumber", this.phonenumber);
            startActivity(okIntent);
        });

        findViewById(R.id.return_button).setOnClickListener(v -> {
            this.finish();
        });
    }

    private String userDataToString() {
        String result = getString(R.string.welcome) + " " + this.firstname + " " + this.name + " !";
        result += "\n" + getString(R.string.label_birthday) + " "  + this.birthday;
        result += "\n" + getString(R.string.label_phone_number) + " "  + this.phonenumber;
        result += "\n" + getString(R.string.label_field_of_expertise) + " "  + this.foe;
        return result;
    }

    // this event will enable the back function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}