package com.example.tp3_exo4;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;

public class Planning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        PlanningModel planningModel = new ViewModelProvider(this).get(PlanningModel.class);
        TextView slot1 = findViewById(R.id.slot_1);
        slot1.setText(planningModel.getSlot1());
        TextView slot2 = findViewById(R.id.slot_2);
        slot2.setText(planningModel.getSlot2());
        TextView slot3 = findViewById(R.id.slot_3);
        slot3.setText(planningModel.getSlot3());
        TextView slot4 = findViewById(R.id.slot_4);
        slot4.setText(planningModel.getSlot4());
    }
}