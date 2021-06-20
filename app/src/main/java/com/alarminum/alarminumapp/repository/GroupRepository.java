package com.alarminum.alarminumapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alarminum.alarminumapp.database.AlarmDatabase;
import com.alarminum.alarminumapp.database.AlarmGroup;
import com.alarminum.alarminumapp.database.AlarmGroupDao;
import com.alarminum.alarminumapp.utils.AppExecutors;

import java.util.List;

public class GroupRepository  {
    private AlarmGroupDao groupDao;

    public GroupRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getInstance(application);
        groupDao = db.groupDao();
    }

    public int getLatestGid() {
        return groupDao.getLatestGroup().gid;
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
