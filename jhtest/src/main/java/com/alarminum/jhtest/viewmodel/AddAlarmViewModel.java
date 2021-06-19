package com.alarminum.jhtest.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.alarminum.jhtest.database.AlarmEntity;
import com.alarminum.jhtest.repository.AlarmRepository;

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
