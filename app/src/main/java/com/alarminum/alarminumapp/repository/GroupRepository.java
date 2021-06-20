package com.alarminum.alarminumapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alarminum.alarminumapp.database.AlarmDatabase;
import com.alarminum.alarminumapp.database.AlarmGroup;
import com.alarminum.alarminumapp.database.AlarmGroupDao;
import com.alarminum.alarminumapp.utils.AppExecutors;
import com.alarminum.alarminumapp.utils.Result;

import java.util.List;

public class GroupRepository  {
    private AlarmGroupDao groupDao;

    public GroupRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getInstance(application);
        groupDao = db.groupDao();
    }

    public void getLatestGid(final RepositoryCallback<Integer> callback) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            try {
                Result<Integer> result = getLatestGidSync();
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Integer> errorResult = new Result.Error<>(e);
                callback.onComplete(errorResult);
            }
        });
    }

    private Result<Integer> getLatestGidSync() {
        try {
            int gid = groupDao.getLatestGroup().gid;
            return new Result.Success<>(gid);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public void insert(AlarmGroup group) {
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            groupDao.insert(group);
        });
    }

    public LiveData<List<AlarmGroup>> getAllGroups() {
        return groupDao.getAllGroups();
    }

    public void delete(AlarmGroup group) {
        AppExecutors.getInstance().getDiskIO().execute(()->{
            groupDao.delete(group);
        });
    }
}
