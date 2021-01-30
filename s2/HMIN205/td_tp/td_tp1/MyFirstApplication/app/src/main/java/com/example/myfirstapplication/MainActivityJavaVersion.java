package com.example.myfirstapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivityJavaVersion extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView datePicked;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java_version);


        // --- Title
        TextView title = new TextView(this);
        title.setText("Java Version");
        title.setTextSize(20);
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        title.setGravity(View.TEXT_ALIGNMENT_CENTER);
        title.setPadding(20,20,20,20);


        // --- Name
        // Label
        TextView nameLabel = new TextView(this);
        nameLabel.setText(R.string.label_name);

        // Input
        EditText nameInput = new EditText(this);
        nameInput.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nameInput.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        // Layout
        LinearLayout nameLayout = new LinearLayout(this);
        nameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        nameLayout.setOrientation(LinearLayout.HORIZONTAL);

        nameLayout.addView(nameLabel);
        nameLayout.addView(nameInput);


        // --- First name
        // Label
        TextView firstNameLabel = new TextView(this);
        firstNameLabel.setText(R.string.label_first_name);

        // Input
        EditText firstNameInput = new EditText(this);
        firstNameInput.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        firstNameInput.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        // Layout
        LinearLayout firstNameLayout = new LinearLayout(this);
        firstNameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        firstNameLayout.setOrientation(LinearLayout.HORIZONTAL);

        firstNameLayout.addView(firstNameLabel);
        firstNameLayout.addView(firstNameInput);


        // --- Birthday
        // Label
        TextView birthdayLabel = new TextView(this);
        birthdayLabel.setText(R.string.label_birthday);

        // Date Picked
        datePicked = new TextView(this);

        // DatePicker Button
        Button datePicker = new Button(this);
        datePicker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        datePicker.setText(R.string.show_calendar);
        datePicker.setTextSize(12);
        datePicker.setTextColor(getResources().getColor(R.color.white));
        datePicker.setAllCaps(false);
        datePicker.setPadding(5,5,5,5);
        datePicker.setBackgroundColor(getResources().getColor(R.color.orange));

        // On click
        datePicker.setOnClickListener(v -> showDatePickerDialog());

        // Layout
        LinearLayout birthdayLayout = new LinearLayout(this);
        birthdayLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        birthdayLayout.setOrientation(LinearLayout.HORIZONTAL);

        birthdayLayout.addView(birthdayLabel);
        birthdayLayout.addView(datePicked);
        birthdayLayout.addView(datePicker);


        // --- Phone number
        // Label
        TextView phoneNumberLabel = new TextView(this);
        phoneNumberLabel.setText(R.string.label_phone_number);

        // Input
        EditText phoneNumberInput = new EditText(this);
        phoneNumberInput.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        phoneNumberInput.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        // Layout
        LinearLayout phoneNumberLayout = new LinearLayout(this);
        phoneNumberLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        phoneNumberLayout.setOrientation(LinearLayout.HORIZONTAL);

        phoneNumberLayout.addView(phoneNumberLabel);
        phoneNumberLayout.addView(phoneNumberInput);


        // --- Field Of Expertise
        // Label
        TextView fieldOfExpLabel = new TextView(this);
        fieldOfExpLabel.setText(R.string.label_field_of_expertise);

        // Input
        EditText fieldOfExpInput = new EditText(this);
        fieldOfExpInput.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fieldOfExpInput.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        // Layout
        LinearLayout fieldOfExpLayout = new LinearLayout(this);
        fieldOfExpLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        fieldOfExpLayout.setOrientation(LinearLayout.HORIZONTAL);

        fieldOfExpLayout.addView(fieldOfExpLabel);
        fieldOfExpLayout.addView(fieldOfExpInput);

        // --- Container Layout
        LinearLayout container = new LinearLayout(this);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        containerParams.setMargins(0, 20, 0, 0);
        container.setLayoutParams(containerParams);
        container.setOrientation(LinearLayout.VERTICAL);

        container.addView(nameLayout);
        container.addView(firstNameLayout);
        container.addView(birthdayLayout);
        container.addView(phoneNumberLayout);
        container.addView(fieldOfExpLayout);

        // --- Submit
        Button submitButton = new Button(this);
        submitButton.setText(R.string.submit);
        submitButton.setTextSize(12);
        submitButton.setTextColor(getResources().getColor(R.color.white));
        submitButton.setBackgroundColor(getResources().getColor(R.color.orange));

        submitButton.setOnClickListener(v -> {

            AlertDialog.Builder userConfirmationDialog = new AlertDialog.Builder(this);
            userConfirmationDialog.setTitle(R.string.dialog_validation_title);
            userConfirmationDialog.setMessage(R.string.dialog_validation_msg);

            // On Yes
            userConfirmationDialog.setPositiveButton(R.string.yes, (dialog, which) -> {
                String userData = getInformation(nameInput, firstNameInput, phoneNumberInput, fieldOfExpInput);
                Toast.makeText(getApplicationContext(), userData, Toast.LENGTH_LONG).show();
            });

            // On No
            userConfirmationDialog.setNegativeButton(R.string.no, (dialog, which) -> {
                Toast.makeText(getApplicationContext(), R.string.cancelled, Toast.LENGTH_LONG).show();
            });

            userConfirmationDialog.show();
        });

        // Layout
        LinearLayout.LayoutParams submitButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        submitButtonParams.gravity = Gravity.CENTER;
        submitButton.setLayoutParams(submitButtonParams);

        // --- Submit and switch page
        Button goToXMLVersionButton = new Button(this);
        goToXMLVersionButton.setText(R.string.go_to_xml_version_btn);
        goToXMLVersionButton.setTextSize(12);
        goToXMLVersionButton.setTextColor(getResources().getColor(R.color.white));
        goToXMLVersionButton.setBackgroundColor(getResources().getColor(R.color.purple_500));

        goToXMLVersionButton.setOnClickListener(v -> {
            openMainActivityXMLVersion();
        });

        // Layout
        LinearLayout.LayoutParams goToXMLVersionButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        goToXMLVersionButtonParams.gravity = Gravity.CENTER;
        goToXMLVersionButton.setLayoutParams(goToXMLVersionButtonParams);


        // --- Submit and switch page
        Button submitAndSwitchButton = new Button(this);
        submitAndSwitchButton.setText(R.string.go_to_new_intent_btn);
        submitAndSwitchButton.setTextSize(12);
        submitAndSwitchButton.setTextColor(getResources().getColor(R.color.white));
        submitAndSwitchButton.setBackgroundColor(getResources().getColor(R.color.green));

        submitAndSwitchButton.setOnClickListener(v -> {

            AlertDialog.Builder userConfirmationDialog = new AlertDialog.Builder(this);
            userConfirmationDialog.setTitle(R.string.dialog_validation_title);
            userConfirmationDialog.setMessage(R.string.dialog_validation_msg);

            // On Yes
            userConfirmationDialog.setPositiveButton(R.string.yes, (dialog, which) -> {
                String userData = getInformation(nameInput, firstNameInput, phoneNumberInput, fieldOfExpInput);
                openNewIntent(userData);
            });

            // On No
            userConfirmationDialog.setNegativeButton(R.string.no, (dialog, which) -> {
                Toast.makeText(getApplicationContext(), R.string.cancelled, Toast.LENGTH_LONG).show();
            });

            userConfirmationDialog.show();
        });

        // Layout
        LinearLayout.LayoutParams submitAndSwitchButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        submitAndSwitchButtonParams.gravity = Gravity.CENTER;
        submitAndSwitchButton.setLayoutParams(submitAndSwitchButtonParams);


        // --- Parent Layout
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.addView(title);
        mainLayout.addView(container);
        mainLayout.addView(submitButton);
        mainLayout.addView(goToXMLVersionButton);
        mainLayout.addView(submitAndSwitchButton);
    }

    private String getInformation(EditText nameInput, EditText firstNameInput, EditText phoneNumberInput, EditText fieldOfExpInput) {
        String result = getString(R.string.welcome) + " " + firstNameInput.getText() + " " + nameInput.getText() + " !";
        result += "\n" + getString(R.string.label_birthday) + " "  + datePicked.getText();
        result += "\n" + getString(R.string.label_phone_number) + " "  + phoneNumberInput.getText();
        result += "\n" + getString(R.string.label_field_of_expertise) + " "  + fieldOfExpInput.getText();
        return result;
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
        String date = month + 1 + "/" + dayOfMonth + "/" + year;
        datePicked.setText(date);
    }

    private void openMainActivityXMLVersion() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openNewIntent(String userData) {
        Intent intent = new Intent(this, MyIntent.class);
        intent.putExtra("userData", userData);
        startActivity(intent);
    }
}