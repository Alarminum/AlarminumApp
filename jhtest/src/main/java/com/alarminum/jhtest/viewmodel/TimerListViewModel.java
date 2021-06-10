package com.alarminum.jhtest.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alarminum.jhtest.database.TimerEntity;
import com.alarminum.jhtest.repository.AlarmRepository;

import java.util.List;

public class TimerListViewModel extends AndroidViewModel {
    private final AlarmRepository alarmRepo;

    private final LiveData<List<TimerEntity>> allTimers;

    public TimerListViewModel (Application application) {
        super(application);
        alarmRepo = new AlarmRepository(application);
        allTimers = alarmRepo.getAllTimers();
    }

    public LiveData<List<TimerEntity>> getAllTimers() { return allTimers; }

    public void insert(TimerEntity timer) {
        alarmRepo.insert(timer);
    }
}
