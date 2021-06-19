package com.alarminum.jhtest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StationDao {
    @Query("SELECT * FROM stations")
    LiveData<List<StationEntity>> getAllStations();

    @Query("SELECT * FROM stations WHERE line_num = :lNum")
    LiveData<List<StationEntity>> getAllStationsWithLine(int lNum);

    @Query("SELECT line_num FROM stations WHERE station_name = :sName")
    int getStationWithName(String sName);

    @Query("SELECT * FROM stations WHERE station_name = :sName AND line_num <> :currentLineNum")
    StationEntity getIntersectionWithNameAndLine(String sName, int currentLineNum);

    @Insert
    void insert(StationEntity station);

    @Delete
    void delete(StationEntity station);
}
