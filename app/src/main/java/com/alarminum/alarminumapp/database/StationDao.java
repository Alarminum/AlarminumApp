package com.alarminum.alarminumapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StationDao {
    @Query("SELECT * FROM stations")
    List<StationEntity> getAllStations();

    @Query("SELECT station_name FROM stations WHERE line_num = :lNum")
    List<String> getAllStationsWithLine(int lNum);

    @Query("SELECT line_num FROM stations WHERE station_name = :sName")
    int getStationWithName(String sName);

    @Query("SELECT * FROM stations WHERE station_name = :sName AND line_num <> :currentLineNum")
    StationEntity getIntersectionWithNameAndLine(String sName, int currentLineNum);
}
