package com.medsminder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.medsminder.model.Medic;
import com.medsminder.model.MedicDatabase;
import com.medsminder.model.MedicsDao;

import java.util.List;

public class MedicRepository {
    private MedicsDao medicsDao;
    private LiveData<List<Medic>> medicsList;

    public MedicRepository(Application application) {
        MedicDatabase medicDatabase = MedicDatabase.getInstance(application);
        medicsDao = medicDatabase.medicsDao();
        medicsList = medicsDao.getAllMedics();
    }

    public LiveData<List<Medic>> getMedicsList() {
        return medicsList;
    }

    public void insert(Medic medic) {
        new InsertMedicAsyncTask(medicsDao).execute(medic);
    }

    public void update(Medic medic) {
        new updateMedicAsyncTask(medicsDao).execute(medic);
    }

    public void delete(Medic medic) {
        new deleteMedicAsyncTask(medicsDao).execute(medic);
    }

    public void deleteAllMedics() {
        new deleteALLMedicAsyncTask(medicsDao).execute();
    }

    private static class InsertMedicAsyncTask extends AsyncTask<Medic, Void, Void> {
        private MedicsDao medicsDao;

        public InsertMedicAsyncTask(MedicsDao medicsDao) {
            this.medicsDao = medicsDao;
        }

        @Override
        protected Void doInBackground(Medic... medics) {
            medicsDao.insert(medics[0]);
            return null;
        }
    }

    private static class updateMedicAsyncTask extends AsyncTask<Medic, Void, Void> {
        private MedicsDao medicsDao;

        public updateMedicAsyncTask(MedicsDao medicsDao) {
            this.medicsDao = medicsDao;
        }

        @Override
        protected Void doInBackground(Medic... medics) {
            medicsDao.update(medics[0]);
            return null;
        }

    }

    private static class deleteMedicAsyncTask extends AsyncTask<Medic, Void, Void> {
        private MedicsDao medicsDao;

        public deleteMedicAsyncTask(MedicsDao medicsDao) {
            this.medicsDao = medicsDao;
        }

        @Override
        protected Void doInBackground(Medic... medics) {
            medicsDao.delete(medics[0]);
            return null;
        }
    }

    private static class deleteALLMedicAsyncTask extends AsyncTask<Medic, Void, Void> {
        private MedicsDao medicsDao;

        public deleteALLMedicAsyncTask(MedicsDao medicsDao) {
            this.medicsDao = medicsDao;
        }

        @Override
        protected Void doInBackground(Medic... medics) {
            medicsDao.deleteAllMedics();
            return null;
        }
    }

}
