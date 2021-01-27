package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView userBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userBirthday = findViewById(R.id.user_birthday);

        findViewById(R.id.show_calendar).setOnClickListener(v -> showDatePickerDialog());

        findViewById(R.id.submit).setOnClickListener(v -> {

            AlertDialog.Builder userConfirmationDialog = new AlertDialog.Builder(this);
            userConfirmationDialog.setTitle(R.string.wait_permission_title);
            userConfirmationDialog.setMessage(R.string.ask_confirmation);

            // On Yes
            userConfirmationDialog.setPositiveButton(R.string.yes, (dialog, which) -> {
                EditText userName = findViewById(R.id.user_name);
                EditText userFirstName = findViewById(R.id.user_first_name);
                TextView userBirthday = findViewById(R.id.user_birthday);
                EditText userPhoneNumber = findViewById(R.id.user_phone_number);
                EditText userFieldOfExp = findViewById(R.id.user_field_of_expertise);

                // Get information give by the user
                String result = getString(R.string.welcome) + " " + userFirstName.getText() + " " + userName.getText() + " !";
                result += "\n" + getString(R.string.label_birthday) + " "  + userBirthday.getText();
                result += "\n" + getString(R.string.label_phone_number) + " "  + userPhoneNumber.getText();
                result += "\n" + getString(R.string.label_field_of_expertise) + " "  + userFieldOfExp.getText();

                // Toast
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            });

            // On No
            userConfirmationDialog.setNegativeButton(R.string.no, (dialog, which) -> {
                Toast.makeText(getApplicationContext(), R.string.cancelled, Toast.LENGTH_LONG).show();
            });

            userConfirmationDialog.show();
        });


        // Go to full java version of this activity
        findViewById(R.id.see_java_version_btn).setOnClickListener(v -> {
            openMainActivityJavaVersion();
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog date = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE)
        );
        date.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = month+1 + "/" + dayOfMonth + "/" + year;
        userBirthday.setText(date);
    }

    public void openMainActivityJavaVersion() {
        Intent intent = new Intent(this, MainActivityJavaVersion.class);
        startActivity(intent);
    }
}