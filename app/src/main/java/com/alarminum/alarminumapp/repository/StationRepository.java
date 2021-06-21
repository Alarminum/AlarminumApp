package com.alarminum.alarminumapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alarminum.alarminumapp.database.StationDao;
import com.alarminum.alarminumapp.database.StationDatabase;
import com.alarminum.alarminumapp.database.StationEntity;
import com.alarminum.alarminumapp.utils.AppExecutors;
import com.alarminum.alarminumapp.utils.Result;

import org.json.JSONObject;

import java.util.List;

public class StationRepository {
    private StationDao stationDao;

    public StationRepository(Application application) {
        StationDatabase db = StationDatabase.getInstance(application);
        stationDao = db.stationDao();
    }

    public List<StationEntity> getAllStations() {
        return stationDao.getAllStations();
    }

    public List<String> getAllStationsWithLine(int lineNum) {
        return stationDao.getAllStationsWithLine(lineNum);
    }

    public void getStationWithName(String sName, final RepositoryCallback<Integer> callback) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            try {
                Result<Integer> result = getStationWithNameSync(sName);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Integer> errorResult = new Result.Error<>(e);
                callback.onComplete(errorResult);
            }
        });
    }

    private Result<Integer> getStationWithNameSync(String sName) {
        try {
            int sid = stationDao.getStationWithName(sName);
            return new Result.Success<>(sid);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }
}
