package com.alarminum.jhtest.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alarminum.jhtest.database.AlarmDatabase;
import com.alarminum.jhtest.database.AlarmGroup;
import com.alarminum.jhtest.database.AlarmGroupDao;
import com.alarminum.jhtest.utils.AppExecutors;

import java.util.List;

public class GroupRepository  {
    private AlarmGroupDao groupDao;

    public GroupRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getInstance(application);
        groupDao = db.groupDao();
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
