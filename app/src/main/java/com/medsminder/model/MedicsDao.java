package com.medsminder.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedicsDao {
    @Insert
    void insert(Medic Medic);

    @Update
    void update(Medic Medic);

    @Delete
    void delete(Medic Medic);

    @Query("DELETE FROM medics_table")
    void deleteAllMedics();

    @Query("SELECT * FROM medics_table")
    LiveData<List<Medic>> getAllMedics();
}
