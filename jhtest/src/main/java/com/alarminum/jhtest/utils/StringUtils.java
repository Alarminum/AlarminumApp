package com.alarminum.jhtest.utils;

public class StringUtils {
    public static String timeToString(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }

    public static String timeToString(int hour, int minute, int second) {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}