package com.alarminum.alarminumapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.alarminum.alarminumapp.database.AlarmDao;
import com.alarminum.alarminumapp.database.AlarmDatabase;
import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.database.TimerDao;
import com.alarminum.alarminumapp.database.TimerEntity;

import java.util.List;

public class AlarmRepository {
    private AlarmDao alarmDao;
    private TimerDao timerDao;
    private LiveData<List<AlarmEntity>> allAlarms;
    private LiveData<List<TimerEntity>> allTimers;

    public AlarmRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getInstance(application);
        alarmDao = db.alarmDao();
        timerDao = db.timerDao();
        allAlarms = alarmDao.getAllAlarms();
        allTimers = timerDao.getAllTimers();
    }

    public void insert(AlarmEntity alarm) {
        AlarmDatabase.dbWriteExecutor.execute(() -> {
            alarmDao.insert(alarm);
        });
    }

    public void insert(TimerEntity timer) {
        AlarmDatabase.dbWriteExecutor.execute(() -> {
            timerDao.insert(timer);
        });
    }

    public LiveData<List<AlarmEntity>> getAllAlarms() {
        return allAlarms;
    }

    public LiveData<List<TimerEntity>> getAllTimers() {
        return allTimers;
    }

}