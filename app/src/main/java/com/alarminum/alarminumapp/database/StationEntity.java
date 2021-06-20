package com.alarminum.alarminumapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stations")
public class StationEntity implements Serializable {
    @PrimaryKey
    public int sid;

    @ColumnInfo(name = "station_name")
    public String stationName;

    @ColumnInfo(name = "line_num")
    public int lineNum;

    public StationEntity() {}

    public StationEntity(int sid, String sName, int lineNum) {
        this.sid = sid;
        this.stationName = sName;
        this.lineNum = lineNum;
    }

    public boolean equals(StationEntity obj) {
        boolean idEqual = (this.sid == obj.sid);
        boolean nameEqual = (this.stationName.equals(obj.stationName));
        boolean lineEqual = (this.lineNum == obj.lineNum);
        return (idEqual && nameEqual && lineEqual);
    }
}



