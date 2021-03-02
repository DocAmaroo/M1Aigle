package com.example.tp3_exo5;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlanningModel extends ViewModel {

    public MutableLiveData<String> slot_1 = new MutableLiveData<>();
    public MutableLiveData<String> slot_2 = new MutableLiveData<>();
    public MutableLiveData<String> slot_3 = new MutableLiveData<>();
    public MutableLiveData<String> slot_4 = new MutableLiveData<>();

    public PlanningModel () {
        slot_1.setValue("Rencontre client Dupont");
        slot_2.setValue("Travailler dossier recrutement");
        slot_3.setValue("Réunion équipe");
        slot_4.setValue("Préparation dossier vente");
    }

    public String getSlot1() { return this.slot_1.getValue(); }
    public void setSlot1(String value) { this.slot_1.setValue(value); }

    public String getSlot2() { return this.slot_2.getValue(); }
    public void setSlot2(String value) { this.slot_2.setValue(value); }

    public String getSlot3() { return this.slot_3.getValue(); }
    public void setSlot3(String value) { this.slot_3.setValue(value); }

    public String getSlot4() { return this.slot_4.getValue(); }
    public void setSlot4(String value) { this.slot_4.setValue(value); }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
