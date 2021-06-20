package com.alarminum.alarminumapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {StationEntity.class}, version = 1, exportSchema = false)
public abstract class StationDatabase extends RoomDatabase {
    private static Context appContext;
    public abstract StationDao stationDao();

    protected StationDatabase() {}

    private static class DBHolder {
        private static final StationDatabase INSTANCE =
                Room.databaseBuilder(appContext, StationDatabase.class, "station_db")
                .createFromAsset("station.db")
                .build();
    }

    public static StationDatabase getInstance(Context appContext) {
        StationDatabase.appContext = appContext;
        return DBHolder.INSTANCE;
    }
}
