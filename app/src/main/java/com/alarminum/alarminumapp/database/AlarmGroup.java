package com.alarminum.alarminumapp.database;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Entity(tableName = "groups")
public class AlarmGroup implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int gid = 1;

    public String label;

    @ColumnInfo(name = "group_type")
    public int groupType;

    public boolean vibration = true;

    public String ringtone = null;

    @ColumnInfo(name = "setting_override")
    public boolean settingOverride = false;

    public boolean activated = true;

    public AlarmGroup() {}

    public AlarmGroup(String label, int groupType, boolean vibration, String ringtone, boolean settingOverride, boolean activated) {
        this.label = label;
        this.groupType = groupType;
        this.vibration = vibration;
        this.ringtone = ringtone;
        this.settingOverride = settingOverride;
        this.activated = activated;
    }

    public AlarmGroup(int gid, String label, int groupType , boolean vibration, String ringtone, boolean settingOverride, boolean activated) {
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
