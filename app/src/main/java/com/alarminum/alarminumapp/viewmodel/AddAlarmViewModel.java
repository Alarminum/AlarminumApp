package com.alarminum.alarminumapp.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.repository.AlarmRepository;

public class AddAlarmViewModel extends AndroidViewModel {
    private final AlarmRepository alarmRepo;

    public AddAlarmViewModel(Application application) {
        super(application);
        alarmRepo = new AlarmRepository(application);
    }

    public void insert(AlarmEntity alarm) {
        alarmRepo.insert(alarm);
    }
}
