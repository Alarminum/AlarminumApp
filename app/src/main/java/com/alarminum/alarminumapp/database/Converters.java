package com.alarminum.alarminumapp.database;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Integer[] fromWeekdays (int weekdays) {
        Integer[] weekdayList = new Integer[7];
        for(int day = 6; day >= 0; day--) {
            weekdayList[day] = weekdays & 1;
            weekdays = weekdays >> 1;
        }
        return weekdayList;
    }

    @TypeConverter
    public static int fromWeekdayList (Integer[] weekdayList) {
        int weekdays = 0;
        for (int day = 0; day < 7; day++) {
            weekdays = weekdays << 1;
            weekdays += weekdayList[day];
        }
        return weekdays;
    }

    @TypeConverter
    public static int fromBoolean(boolean b) {
        return b ? 1 : 0;
    }

    @TypeConverter
    public static boolean fromIntegerToBoolean(int i) {
        return i==1;
    }
}
