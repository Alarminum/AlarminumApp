package com.alarminum.jhtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.jhtest.database.TimerEntity;


public class TimerItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView timerTitleView;
    private final TextView timerTimeView;

    private TimerItemViewHolder(View itemView) {
        super(itemView);
        timerTitleView = itemView.findViewById(R.id.timer_title);
        timerTimeView = itemView.findViewById(R.id.timer_time);
    }

    public void bind(TimerEntity timer) {
        timerTitleView.setText(timer.label);
        timerTimeView.setText(String.format("%02d:%02d:%02d", timer.hour, timer.minute,timer.second));
    }

    static TimerItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timer_item, parent, false);
        return new TimerItemViewHolder(view);
    }
}
