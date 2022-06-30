package com.medsminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.medsminder.model.Medic;

import java.util.List;

public class MedicViewModel extends AndroidViewModel {

    private MedicRepository medicRepository;
    private LiveData<List<Medic>> medicsList;


    public MedicViewModel(@NonNull Application application) {
        super(application);
        medicRepository = new MedicRepository(application);
        medicsList = medicRepository.getMedicsList();
    }

    public void insert(Medic medic) {
        medicRepository.insert(medic);
    }

    public void update(Medic medic) {
        medicRepository.update(medic);
    }

    public void delete(Medic medic) {
        medicRepository.delete(medic);
    }

    public void deleteAllTasks() {
        medicRepository.deleteAllMedics();
    }

    public LiveData<List<Medic>> getMedicsList() {
        return medicsList;
    }


}
