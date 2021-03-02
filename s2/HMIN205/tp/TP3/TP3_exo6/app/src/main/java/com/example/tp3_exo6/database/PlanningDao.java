package com.example.tp3_exo6.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tp3_exo6.models.Planning;

import java.util.List;

@Dao
public interface PlanningDao {
    @Insert
    void insert(Planning planning);

    @Insert
    void insertAll(Planning... plannings);

    @Update
    void update(Planning planning);

    @Query("SELECT * FROM Planning ORDER BY uid")
    LiveData<List<Planning>> getAllPlanning();

    @Query("SELECT * FROM Planning WHERE uid = :id")
    LiveData<Planning> getTodayPlanning(int id);
}
