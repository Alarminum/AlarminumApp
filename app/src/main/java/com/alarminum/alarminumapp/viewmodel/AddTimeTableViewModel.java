package com.alarminum.alarminumapp.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.alarminum.alarminumapp.repository.TimeTableRepository;
import com.alarminum.alarminumapp.utils.Result;

import org.json.JSONObject;

public class AddTimeTableViewModel extends ViewModel {
    private final TimeTableRepository timeTableRepo;

    public AddTimeTableViewModel() {
        super();
        timeTableRepo = new TimeTableRepository();
    }

    public void makeTimeTableRequest(String username, String password) {
        timeTableRepo.makeTimeTableRequest(username, password, result -> {
            if(result instanceof Result.Success) {

            } else {
                Log.d("TimeTableRequest", ((Result.Error)result).exception + " : Time Table request failed");
            }
        });
    }
}
