package com.alarminum.alarminumapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.alarminum.alarminumapp.repository.StationRepository;
import com.google.android.gms.common.internal.ResourceUtils;

import java.util.List;

public class AddMetroViewModel extends AndroidViewModel {
    private final StationRepository stationRepo;

    public AddMetroViewModel(Application application) {
        super(application);
        stationRepo = new StationRepository(application);
    }

    public List<String> getAllStationWithLine(int lineNum) {
        return stationRepo.getAllStationsWithLine(lineNum);
    }

    public void getStationWithName(String sName) {
        try {
            stationRepo.getStationWithName(sName, result -> {

            });
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
