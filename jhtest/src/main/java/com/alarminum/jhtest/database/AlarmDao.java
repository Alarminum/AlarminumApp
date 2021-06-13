package com.alarminum.jhtest.database;

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

    @Insert
    void insert(AlarmEntity alarm);

    @Update
    void update(AlarmEntity alarm);

    @Query("DELETE FROM alarms")
    void deleteAll();

    @Delete
    void delete(AlarmEntity alarm);
}