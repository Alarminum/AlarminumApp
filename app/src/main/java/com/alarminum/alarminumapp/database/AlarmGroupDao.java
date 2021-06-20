package com.alarminum.alarminumapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmGroupDao {
    @Query("SELECT * FROM groups")
    LiveData<List<AlarmGroup>> getAllGroups();

    @Query("SELECT * FROM groups ORDER BY gid DESC LIMIT 1")
    AlarmGroup getLatestGroup();

    @Insert
    void insert(AlarmGroup group);

    @Update
    void update(AlarmGroup group);

    @Query("DELETE FROM groups")
    void deleteAll();

    @Delete
    void delete(AlarmGroup group);
}
