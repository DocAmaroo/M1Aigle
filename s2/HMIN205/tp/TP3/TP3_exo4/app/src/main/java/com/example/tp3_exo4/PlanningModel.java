package com.example.tp3_exo4;

import androidx.lifecycle.ViewModel;

public class PlanningModel extends ViewModel {

    private String slot_1 = "Rencontre client Dupont";
    private String slot_2 = "Travailler dossier recrutement";
    private String slot_3 = "Réunion équipe";
    private String slot_4 = "Préparation dossier vente";

    public String getSlot1() { return this.slot_1; }
    public String getSlot2() { return this.slot_2; }
    public String getSlot3() { return this.slot_3; }
    public String getSlot4() { return this.slot_4; }
}
