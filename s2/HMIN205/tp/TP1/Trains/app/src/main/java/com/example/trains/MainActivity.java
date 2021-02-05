package com.example.trains;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Schedule> schedules = new ArrayList<>();
    private Spinner startSpinner;
    private Spinner destinationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String st1 = "Montpellier"; String st2 = "Marseille"; String st3 = "Lyon"; String st4 = "Paris"; String st5 = "Béziers"; String st6 = "Toulouse";

        String h1 = "8h14";
        String h2 = "9h15";
        String h3 = "10h23";
        String h4 = "11h45";
        String h5 = "12h12";
        String h6 = "15h34";
        String h7 = "16h15";
        String h8 = "17h26";
        String h9 = "18h45";
        String h10 = "19h02";
        String h11 = "20h18";
        String h12 = "22h53";


        Schedule sc1 = new Schedule(st1, h1, st4, h7);
        Schedule sc2 = new Schedule(st1, h2, st4, h8);
        Schedule sc3 = new Schedule(st1, h3, st4, h9);
        Schedule sc4 = new Schedule(st1, h4, st4, h10);
        Schedule sc5 = new Schedule(st1, h5, st4, h11);
        Schedule sc6 = new Schedule(st1, h6, st4, h12);
        Schedule sc7 = new Schedule(st2, h1, st1, h7);
        Schedule sc8 = new Schedule(st2, h2, st3, h8);
        Schedule sc9 = new Schedule(st2, h3, st4, h9);
        Schedule sc10 = new Schedule(st2, h4, st5, h10);
        Schedule sc11 = new Schedule(st3, h5, st4, h11);
        Schedule sc12 = new Schedule(st3, h6, st1, h12);
        Schedule sc13 = new Schedule(st3, h1, st2, h7);
        Schedule sc14 = new Schedule(st3, h2, st3, h8);
        Schedule sc15 = new Schedule(st4, h3, st1, h9);
        Schedule sc16 = new Schedule(st4, h4, st2, h10);
        Schedule sc17 = new Schedule(st4, h5, st3, h11);
        Schedule sc18 = new Schedule(st4, h6, st5, h12);
        Schedule sc19 = new Schedule(st4, h1, st6, h7);
        Schedule sc20 = new Schedule(st5, h2, st2, h8);
        Schedule sc21 = new Schedule(st5, h3, st3, h9);
        Schedule sc22 = new Schedule(st5, h4, st4, h10);
        Schedule sc23 = new Schedule(st6, h5, st1, h11);
        Schedule sc24 = new Schedule(st6, h6, st2, h12);
        this.schedules.add(sc1);
        this.schedules.add(sc2);
        this.schedules.add(sc3);
        this.schedules.add(sc4);
        this.schedules.add(sc5);
        this.schedules.add(sc6);
        this.schedules.add(sc7);
        this.schedules.add(sc8);
        this.schedules.add(sc9);
        this.schedules.add(sc10);
        this.schedules.add(sc11);
        this.schedules.add(sc12);
        this.schedules.add(sc13);
        this.schedules.add(sc14);
        this.schedules.add(sc15);
        this.schedules.add(sc16);
        this.schedules.add(sc17);
        this.schedules.add(sc18);
        this.schedules.add(sc19);
        this.schedules.add(sc20);
        this.schedules.add(sc21);
        this.schedules.add(sc22);
        this.schedules.add(sc23);
        this.schedules.add(sc24);

        this.startSpinner = (Spinner) findViewById(R.id.spinner_start);
        this.startSpinner.setOnItemSelectedListener(this);
        this.startSpinner.setSelection(0);

        this.destinationSpinner = (Spinner) findViewById(R.id.spinner_destination);
        this.destinationSpinner.setOnItemSelectedListener(this);
        this.destinationSpinner.setSelection(0);

        List<String> stations = new ArrayList<>();
        stations.add("Choisir une station");
        stations.add("Montpellier");
        stations.add("Marseille");
        stations.add("Lyon");
        stations.add("Paris");
        stations.add("Béziers");
        stations.add("Toulouse");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(dataAdapter);
        destinationSpinner.setAdapter(dataAdapter);


        Button searchButton = findViewById(R.id.btn_search);
        searchButton.setOnClickListener(v -> {

            String start = startSpinner.getSelectedItem().toString();
            String destination = destinationSpinner.getSelectedItem().toString();

            if (start.equals(destination)) {
                Toast.makeText(this, "Choissisez-un départ et une arrivée différente s'il vous plaît", Toast.LENGTH_SHORT).show();
            } else {
                LinearLayout scrollView = findViewById(R.id.schedule_container);
                scrollView.removeAllViews();
                for (Schedule sch : this.schedules) {
                    if (sch.getsA().equals(start) && sch.getsB().equals(destination)) {
                        TextView text = new TextView(this);
                        text.setText(sch.toString());
                        text.setTextSize(15);
                        text.setGravity(Gravity.CENTER);
                        scrollView.addView(text);
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}

class Schedule {
    private final String sA;
    private final String sB;
    private final String hStart;
    private final String hArrival;

    public Schedule(String stationA, String hourStart, String stationB, String hourArrival) {
        this.sA = stationA;
        this.hStart = hourStart;
        this.sB = stationB;
        this.hArrival = hourArrival;
    }

    public String getsA() {
        return sA;
    }

    public String getsB() {
        return sB;
    }

    public String toString() {
        return this.getsA() + " (" + this.hStart + ") - " + this.getsB() + " (" + this.hArrival + ") ";
    }
}