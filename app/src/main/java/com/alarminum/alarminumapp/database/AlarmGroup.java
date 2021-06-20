package com.alarminum.alarminumapp.database;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "groups")
public class AlarmGroup implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int gid = 1;

    public String label;

    @ColumnInfo(name = "group_type")
    public int groupType;

    public int vibration = 1;

    public String ringtone = null;

    @ColumnInfo(name = "setting_override")
    public int settingOverride = 0;

    public int activated = 1;

    public AlarmGroup() {}

    public AlarmGroup(String label, int groupType, int vibration, String ringtone, int settingOverride, int activated) {
        this.label = label;
        this.groupType = groupType;
        this.vibration = vibration;
        this.ringtone = ringtone;
        this.settingOverride = settingOverride;
        this.activated = activated;
    }

    public AlarmGroup(int gid, String label, int groupType , int vibration, String ringtone, int settingOverride, int activated) {
        this.gid = gid;
        this.label = label;
        this.groupType = groupType;
        this.vibration = vibration;
        this.ringtone = ringtone;
        this.settingOverride = settingOverride;
        this.activated = activated;
    }

    public boolean equals(@Nullable AlarmGroup obj) {
        boolean labelEqual = this.label.equals(obj.label);
        boolean typeEqual = (this.groupType == obj.groupType);
        boolean vibEqual = (this.vibration == obj.vibration);
        boolean ringtoneEqual = this.ringtone.equals(obj.ringtone);
        boolean overrideEqual = (this.settingOverride == obj.settingOverride);
        boolean activeEqual = (this.activated == obj.activated);

        return (labelEqual
                && typeEqual
                && vibEqual
                && ringtoneEqual
                && overrideEqual
                && activeEqual);
    }

}
