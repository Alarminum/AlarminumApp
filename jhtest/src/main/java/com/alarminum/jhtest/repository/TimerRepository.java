package com.alarminum.jhtest.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.alarminum.jhtest.database.AlarmDatabase;
import com.alarminum.jhtest.database.TimerDao;
import com.alarminum.jhtest.database.TimerEntity;
import com.alarminum.jhtest.utils.AppExecutors;

import java.util.List;

public class TimerRepository {
    private TimerDao timerDao;

    public TimerRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getInstance(application);
        timerDao = db.timerDao();
    }

    public void insert(TimerEntity timer) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            timerDao.insert(timer);
        });
    }

    public void delete(TimerEntity timer) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            timerDao.delete(timer);
        });
    }

    public LiveData<List<TimerEntity>> getAllTimers() {
        return timerDao.getAllTimers();
    }

}
