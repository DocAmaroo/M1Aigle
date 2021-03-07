package com.example.tp3_exo1;

import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String userId;

    private TextView TXT_user_id;
    private EditText userNameEntry;
    private EditText firstNameEntry;
    private EditText phoneEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.TXT_user_id = findViewById(R.id.user_id);
        this.userNameEntry = findViewById(R.id.user_name_entry);
        this.firstNameEntry = findViewById(R.id.user_firstname_entry);
        this.phoneEntry = findViewById(R.id.user_phone_entry);

        if (savedInstanceState == null) {
            generateRandomId();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save user id
        savedInstanceState.putString("USER_ID", this.userId);

        EditText password = findViewById(R.id.user_password_entry);
        password.setText(null);

        // Get all entries
        String username = this.userNameEntry.getText().toString();
        String firstname = this.firstNameEntry.getText().toString();
        String phone = this.phoneEntry.getText().toString();

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
                this.TXT_user_id.setText(this.userId);
            }

            // restore entries
            if (savedInstanceState.containsKey("USER_ENTRIES")) {
                ArrayList<String> entries = savedInstanceState.getStringArrayList("USER_ENTRIES");
                this.userNameEntry.setText(entries.get(0));
                this.firstNameEntry.setText(entries.get(1));
                this.phoneEntry.setText(entries.get(2));
            }
        }
    }

    private void generateRandomId() {
        long number = Math.round((Math.random() * 10000));
        this.userId = "i" + number;
        this.TXT_user_id.setText(userId);
    }
}