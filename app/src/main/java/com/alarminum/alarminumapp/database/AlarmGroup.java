package com.alarminum.alarminumapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AlarmGroup {
    @PrimaryKey(autoGenerate = true)
    public int gid;


    public int activated = 1;

}
