package com.alarminum.alarminumapp.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.alarminum.alarminumapp.database.TimerEntity;
import com.alarminum.alarminumapp.repository.TimerRepository;

public class AddTimerViewModel extends AndroidViewModel {
    private final TimerRepository timerRepo;

    public AddTimerViewModel(Application application) {
        super(application);
        timerRepo = new TimerRepository(application);
    }

    public void insert(TimerEntity timer) {
        timerRepo.insert(timer);
    }
}
