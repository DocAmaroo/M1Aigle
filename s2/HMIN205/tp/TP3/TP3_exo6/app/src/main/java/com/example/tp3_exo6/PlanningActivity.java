package com.example.tp3_exo6;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tp3_exo6.models.Planning;
import com.example.tp3_exo6.viewmodel.PlanningViewModel;

public class PlanningActivity extends AppCompatActivity {

    private TextView slot1;
    private TextView slot2;
    private TextView slot3;
    private TextView slot4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        // Par practicité on représente le jour par un entien
        int day = 1;

        this.slot1 = findViewById(R.id.slot_1);
        this.slot2 = findViewById(R.id.slot_2);
        this.slot3 = findViewById(R.id.slot_3);
        this.slot4 = findViewById(R.id.slot_4);

        // Insertions de planning dans le base de donnée
        Planning p1 = new Planning("Rencontre client Dupont", "Travailler dossier recrutement", "Réunion équipe", "Préparation dossier vente");
        Planning p2 = new Planning("Grasse matinee", "Apero", "Petanque", "Apero");
        PlanningViewModel planningViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(PlanningViewModel.class);
        planningViewModel.insertAllPlanning(p1, p2);

        // On lit notre dans planning du jour 1 via notre base de donnée
        planningViewModel.getTodayPlanning(day).observe(this, planning -> {
            this.slot1.setText(planning.getSlot1());
            this.slot2.setText(planning.getSlot2());
            this.slot3.setText(planning.getSlot3());
            this.slot4.setText(planning.getSlot4());
        });
    }
}