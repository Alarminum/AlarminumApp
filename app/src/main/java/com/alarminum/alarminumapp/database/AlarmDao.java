package com.alarminum.alarminumapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM alarms")
    LiveData<List<AlarmEntity>> getAllAlarms();

    @Query("SELECT * FROM alarms WHERE :gid")
    LiveData<List<AlarmEntity>> getAlarmOfGroup(int gid);

    @Insert
    void insert(AlarmEntity alarm);

    @Update
    void update(AlarmEntity alarm);

    @Query("DELETE FROM alarms")
    void deleteAll();

    @Delete
    void delete(AlarmEntity alarm);
}
