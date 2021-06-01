package com.alarminum.alarminumapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.database.AlarmEntity;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private final TextView alarmTitleView;
    private final TextView alarmTimeView;

    private AlarmViewHolder(View itemView) {
        super(itemView);
        alarmTitleView = itemView.findViewById(R.id.alarm_title);
        alarmTimeView = itemView.findViewById(R.id.alarm_time);
    }

    public void bind(AlarmEntity alarm) {
        alarmTitleView.setText(alarm.label);
        alarmTimeView.setText(String.format("%02d:%02d", alarm.hour, alarm.minute));
    }

    static AlarmViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new AlarmViewHolder(view);
    }
}
