package com.medsminder.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

@Database(entities = {Medic.class}, version = 1)
public abstract class MedicDatabase extends RoomDatabase {

    private static MedicDatabase instance;
    private static Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized MedicDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MedicDatabase.class, "medicDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    public abstract MedicsDao medicsDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MedicsDao taskDao;

        public PopulateDbAsyncTask(MedicDatabase taskDatabase) {
            this.taskDao = taskDatabase.medicsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("8:00");
            arrayList.add("12:00");
            arrayList.add("23:00");

            taskDao.insert(new Medic("Amlip", "BP", "5 mg", "red", arrayList, 1));
            taskDao.insert(new Medic("Glucoret", "Diabetics", "200 mg", "red", arrayList, 1));
            taskDao.insert(new Medic("dolo", "fever", "3 oz", "red", arrayList, 1));

            return null;
        }
    }
}
