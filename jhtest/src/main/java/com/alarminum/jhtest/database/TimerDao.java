package com.alarminum.jhtest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimerDao {
    @Query("SELECT * FROM timers")
    LiveData<List<TimerEntity>> getAllTimers();

    @Insert
    void insert(TimerEntity timer);

    @Update
    void update(TimerEntity timer);

    @Query("DELETE FROM timers")
    void deleteAll();

    @Delete
    void delete(TimerEntity timer);
}
