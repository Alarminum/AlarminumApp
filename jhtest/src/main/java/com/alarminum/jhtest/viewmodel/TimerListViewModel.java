package com.alarminum.jhtest.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alarminum.jhtest.database.TimerEntity;
import com.alarminum.jhtest.repository.TimerRepository;

import java.util.List;

public class TimerListViewModel extends AndroidViewModel {
    private final TimerRepository timerRepo;

    public TimerListViewModel (Application application) {
        super(application);
        timerRepo = new TimerRepository(application);
    }

    public LiveData<List<TimerEntity>> getAllTimers() { return timerRepo.getAllTimers(); }

    public void insert(TimerEntity timer) {
        timerRepo.insert(timer);
    }
}
