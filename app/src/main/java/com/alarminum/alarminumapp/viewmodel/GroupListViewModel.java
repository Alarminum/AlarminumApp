package com.alarminum.alarminumapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alarminum.alarminumapp.database.AlarmGroup;
import com.alarminum.alarminumapp.repository.GroupRepository;

import java.util.List;

public class GroupListViewModel extends AndroidViewModel {
    private final GroupRepository groupRepo;

    public GroupListViewModel(Application application) {
        super(application);
        groupRepo = new GroupRepository(application);
    }

    public LiveData<List<AlarmGroup>> getAllGroups() { return groupRepo.getAllGroups(); }

    public void insert(AlarmGroup group) { groupRepo.insert(group); }

    public void delete(AlarmGroup group) { groupRepo.delete(group); }
}
