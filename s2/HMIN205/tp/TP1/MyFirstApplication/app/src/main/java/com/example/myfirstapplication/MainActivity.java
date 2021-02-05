package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
            userConfirmationDialog.setTitle(R.string.dialog_validation_title);
            userConfirmationDialog.setMessage(R.string.dialog_validation_msg);

            // On Yes
            userConfirmationDialog.setPositiveButton(R.string.yes, (dialog, which) -> {
                Toast.makeText(getApplicationContext(), userDataToString(), Toast.LENGTH_LONG).show();
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

        // Submit and switch page
        findViewById(R.id.go_next_intent).setOnClickListener(v -> {

            AlertDialog.Builder userConfirmationDialog = new AlertDialog.Builder(this);
            userConfirmationDialog.setTitle(R.string.dialog_validation_title);
            userConfirmationDialog.setMessage(R.string.dialog_validation_msg);

            // On Yes
            userConfirmationDialog.setPositiveButton(R.string.yes, (dialog, which) -> {
                openNewIntent();
            });

            // On No
            userConfirmationDialog.setNegativeButton(R.string.no, (dialog, which) -> {
                Toast.makeText(getApplicationContext(), R.string.cancelled, Toast.LENGTH_LONG).show();
            });

            userConfirmationDialog.show();

        });
    }

    private ArrayList<String> getInformation() {
        ArrayList<String> userData = new ArrayList<>();

        EditText userName = findViewById(R.id.user_name);
        EditText userFirstName = findViewById(R.id.user_first_name);
        TextView userBirthday = findViewById(R.id.user_birthday);
        EditText userPhoneNumber = findViewById(R.id.user_phone_number);
        EditText userFieldOfExp = findViewById(R.id.user_field_of_expertise);


        userData.add(userName.getText().toString());
        userData.add(userFirstName.getText().toString());
        userData.add(userBirthday.getText().toString());
        userData.add(userPhoneNumber.getText().toString());
        userData.add(userFieldOfExp.getText().toString());

        return userData;
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

    private String userDataToString() {
        ArrayList<String> userData = getInformation();
        String result = getString(R.string.welcome) + " " + userData.get(1) + " " + userData.get(0) + " !";
        result += "\n" + getString(R.string.label_birthday) + " "  + userData.get(2);
        result += "\n" + getString(R.string.label_phone_number) + " "  + userData.get(3);
        result += "\n" + getString(R.string.label_field_of_expertise) + " "  + userData.get(4);

        return result;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = month+1 + "/" + dayOfMonth + "/" + year;
        userBirthday.setText(date);
    }

    private void openMainActivityJavaVersion() {
        Intent intent = new Intent(this, MainActivityJavaVersion.class);
        startActivity(intent);
    }

    private void openNewIntent() {
        ArrayList<String> userData = getInformation();
        Intent intent = new Intent(this, MyIntent.class);
        intent.putExtra("userName", userData.get(0));
        intent.putExtra("userFirstName", userData.get(1));
        intent.putExtra("userBirhday", userData.get(2));
        intent.putExtra("userPhoneNumber", userData.get(3));
        intent.putExtra("userFieldOfExp", userData.get(4));
        startActivity(intent);
    }


}