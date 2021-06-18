package com.alarminum.jhtest.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.alarminum.jhtest.database.AlarmDao;
import com.alarminum.jhtest.database.AlarmDatabase;
import com.alarminum.jhtest.database.AlarmEntity;
import com.alarminum.jhtest.utils.AppExecutors;

import java.util.List;

public class AlarmRepository {
    private AlarmDao alarmDao;

    public AlarmRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getInstance(application);
        alarmDao = db.alarmDao();
    }

    public void insert(AlarmEntity alarm) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            alarmDao.insert(alarm);
        });
    }

    public LiveData<List<AlarmEntity>> getAllAlarms() {
        return alarmDao.getAllAlarms();
    }

    public void delete(AlarmEntity alarm) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            alarmDao.delete(alarm);
        });
    }

}