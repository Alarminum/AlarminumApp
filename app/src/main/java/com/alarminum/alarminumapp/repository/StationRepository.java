package com.alarminum.alarminumapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alarminum.alarminumapp.database.StationDao;
import com.alarminum.alarminumapp.database.StationDatabase;
import com.alarminum.alarminumapp.database.StationEntity;

import java.util.List;

public class StationRepository {
    private StationDao stationDao;

    public StationRepository(Application application) {
        StationDatabase db = StationDatabase.getInstance(application);
        stationDao = db.stationDao();
    }

    public LiveData<List<StationEntity>> getAllStations() {
        return stationDao.getAllStations();
    }

    public LiveData<List<StationEntity>> getAllStationsWithLine(int lineNum) {
        return stationDao.getAllStationsWithLine(lineNum);
    }

    public int getStationWithName(String sName) {
        return stationDao.getStationWithName(sName);
    }

    public StationEntity getIntersectionWithNameAndLine(String sName, int currentLinenum) {
        return stationDao.getIntersectionWithNameAndLine(sName, currentLinenum);
    }
}
