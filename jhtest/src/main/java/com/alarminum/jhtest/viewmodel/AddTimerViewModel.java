package com.alarminum.jhtest.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.alarminum.jhtest.database.TimerEntity;
import com.alarminum.jhtest.repository.TimerRepository;

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
