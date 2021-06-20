package com.alarminum.alarminumapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alarminum.alarminumapp.database.AlarmDatabase;
import com.alarminum.alarminumapp.database.AlarmGroup;
import com.alarminum.alarminumapp.database.AlarmGroupDao;
import com.alarminum.alarminumapp.utils.AppExecutors;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GroupRepository  {
    private AlarmGroupDao groupDao;

    public GroupRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getInstance(application);
        groupDao = db.groupDao();
    }

    public long insert(AlarmGroup group) {
        AtomicLong gid = new AtomicLong();
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            gid.set(groupDao.insert(group));
        });
        return gid.get();
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
