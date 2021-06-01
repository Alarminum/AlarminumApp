package com.alarminum.alarminumapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.database.TimerEntity;
import com.alarminum.alarminumapp.repository.AlarmRepository;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {
    private final AlarmRepository alarmRepo;

    private final LiveData<List<AlarmEntity>> allAlarms;
    private final LiveData<List<TimerEntity>> allTimers;

    public AlarmViewModel (Application application) {
        super(application);
        alarmRepo = new AlarmRepository(application);
        allAlarms = alarmRepo.getAllAlarms();
        allTimers = alarmRepo.getAllTimers();
    }

    public LiveData<List<AlarmEntity>> getAllAlarms() { return allAlarms; }

    public void insert(AlarmEntity alarm) {
        alarmRepo.insert(alarm);
    }

    public LiveData<List<TimerEntity>> getAllTimers() { return allTimers; }

    public void insert(TimerEntity timer) {
        alarmRepo.insert(timer);
    }
}
