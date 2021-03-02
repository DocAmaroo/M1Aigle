package com.example.tp3_exo6.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tp3_exo6.database.AppDatabase;
import com.example.tp3_exo6.models.Planning;

import java.util.List;

public class PlanningViewModel extends AndroidViewModel {

    private AppDatabase db;

    public PlanningViewModel(@NonNull Application application) {
        super(application);
        this.db = AppDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Planning>> getAllPlannings() { return db.planningDao().getAllPlanning(); }

    public LiveData<Planning> getTodayPlanning(int id) { return db.planningDao().getTodayPlanning(id); }

    public void insertPlanning(Planning planning) {
        db.planningDao().insert(planning);
    }

    public void insertAllPlanning(Planning... plannings) {
        db.planningDao().insertAll(plannings);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
