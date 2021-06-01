package com.alarminum.alarminumapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AlarmEntity.class, TimerEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AlarmDatabase extends RoomDatabase {
    // Apply singleton pattern(Lazy holder)
    // Make constructor private
    private static Context appContext;
    public abstract AlarmDao alarmDao();
    public abstract TimerDao timerDao();

    // Get application context when initialize DB
    // At this time, inner class(DBHolder) is not initialized.
    protected AlarmDatabase() {};
    private static final int THREADS = 4;
    public static final ExecutorService dbWriteExecutor =
            Executors.newFixedThreadPool(THREADS);

    // Define INSTANCE in DBHolder
    // It will be invoked when 'DBHolder.INSTANCE' is referred.
    private static class DBHolder {
        private static final AlarmDatabase INSTANCE =
            Room.databaseBuilder(appContext, AlarmDatabase.class, "alarm_db")
                    .addCallback(sRoomDBCallback)
                    .build();
    }

    public static AlarmDatabase getInstance(Context appContext) {
        AlarmDatabase.appContext = appContext;
        return DBHolder.INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDBCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            dbWriteExecutor.execute(()->{
                AlarmDao dao = DBHolder.INSTANCE.alarmDao();
                dao.deleteAll();
            });
        }
    };
}
