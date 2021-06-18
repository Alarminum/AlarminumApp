package com.alarminum.jhtest.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alarminum.jhtest.database.AlarmEntity;
import com.alarminum.jhtest.repository.AlarmRepository;


import java.util.List;

public class AlarmListViewModel extends AndroidViewModel {
    private final AlarmRepository alarmRepo;

    public AlarmListViewModel(Application application) {
        super(application);
        alarmRepo = new AlarmRepository(application);
    }

    public LiveData<List<AlarmEntity>> getAllAlarms() { return alarmRepo.getAllAlarms(); }

    public void insert(AlarmEntity alarm) {
        alarmRepo.insert(alarm);
    }

    public void delete(AlarmEntity alarm) { alarmRepo.delete(alarm);}
}
