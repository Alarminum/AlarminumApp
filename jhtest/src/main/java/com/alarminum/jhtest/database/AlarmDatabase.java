package com.alarminum.jhtest.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alarminum.jhtest.utils.AppExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AlarmEntity.class, TimerEntity.class, AlarmGroup.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AlarmDatabase extends RoomDatabase {
    // Apply singleton pattern(Lazy holder)
    // Make constructor private
    private static Context appContext;
    public abstract AlarmDao alarmDao();
    public abstract TimerDao timerDao();
    public abstract AlarmGroupDao groupDao();

    // Get application context when initialize DB
    // At this time, inner class(DBHolder) is not initialized.
    protected AlarmDatabase() {};

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

            AppExecutors.getInstance().getDiskIO().execute(() -> {
                AlarmGroupDao groupDao = DBHolder.INSTANCE.groupDao();
                AlarmDao alarmDao = DBHolder.INSTANCE.alarmDao();
                Integer[] weekdays = new Integer[] {1,1,1,1,0,1,1};
                groupDao.insert(new AlarmGroup(1, "기본 알람 그룹", 0, 0,null, 0, 1));
                groupDao.insert(new AlarmGroup(2, "기본 타이머 그룹", 1, 0,null, 0, 1));
                alarmDao.insert((new AlarmEntity("test1",12,30, weekdays,1,null,0,1,1)));
                alarmDao.insert((new AlarmEntity("test2",12,30, weekdays,1,null,0,1,1)));
                alarmDao.insert((new AlarmEntity("test3",12,30, weekdays,1,null,0,1,1)));
                alarmDao.insert((new AlarmEntity("test4",12,30, weekdays,1,null,0,1,1)));
                alarmDao.insert((new AlarmEntity("test5",12,30, weekdays,1,null,0,1,1)));
                alarmDao.insert((new AlarmEntity("test6",12,30, weekdays,1,null,0,1,1)));

            });
        }
    };
}
