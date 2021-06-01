package com.alarminum.alarminumapp.database;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Arrays;

@Entity(tableName = "alarms")
public class AlarmEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int aid = 0;             // 알람의 id. 기본키이며 1씩 자동적으로 증가함.

    public String label;            // 알람의 제목

    public int hour = 0;            // 알람 시간 중 시 부분. 초기값은 0(시)

    public int minute = 0;          // 알람 시간 중 분 부분. 초기값은 0(분)

    @ColumnInfo(name = "weekday_list")
    public Integer[] weekdayList;   // 요일 배열(월요일~일요일)

    @ColumnInfo(name = "snooze_minute")
    public int snoozeMinute = 5;    // 연기한 알람을 다시 울리기까지 대기하는 시간. 초기값은 5(분)

    public int vibration = 1;       // 진동 알림 여부. 초기값은 1(true)

    public String ringtone = null;  // 벨소리 이름. 초기값은 null

    @ColumnInfo(name = "for_once")
    public int forOnce = 1;         // 한 번 울린 뒤 삭제 여부. 초기값은 1(true)

    public int activated = 1;       // 활성화 여부. 초기값은 1(true)

    @ColumnInfo(name  = "parent_gid")
    public int parentGid = 0;       // 알람이 소속된 그룹. 초기값은 0(기본 그룹)

    public AlarmEntity() {}

    public AlarmEntity(String label, int hour, int min, Integer[] weekdayList, int snoozeMinute, int vibration, String ringtone, int forOnce, int activated, int parentGid) {
        this.label = label;
        this.hour = hour;
        this.minute = min;
        this.weekdayList = weekdayList;
        this.snoozeMinute = snoozeMinute;
        this.vibration = vibration;
        this.ringtone = ringtone;
        this.forOnce = forOnce;
        this.activated = activated;
        this.parentGid = parentGid;
    }

    public boolean equals(@Nullable AlarmEntity obj) {
        boolean labelEqual = this.label.equals(obj.label);
        boolean timeEqual = (this.hour == obj.hour) && (this.minute == obj.minute);
        boolean weekDayEqual = Arrays.equals(this.weekdayList, obj.weekdayList);
        boolean snoozeEqual = (this.snoozeMinute == obj.snoozeMinute);
        boolean vibEqual = (this.vibration == obj.vibration);
        boolean ringtoneEqual = this.ringtone.equals(obj.ringtone);
        boolean onceEqual = (this.forOnce == obj.forOnce);
        boolean activeEqual = (this.activated == obj.activated);
        boolean gidEqual = (this.parentGid == obj.parentGid);

        return (labelEqual
                && timeEqual
                && weekDayEqual
                && snoozeEqual
                && vibEqual
                && ringtoneEqual
                && onceEqual
                && activeEqual
                && gidEqual);
    }
}
