package com.alarminum.jhtest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.jhtest.database.AlarmEntity;


public class AlarmItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView alarmTitleView;
    private final TextView alarmTimeView;

    private AlarmItemViewHolder(View itemView) {
        super(itemView);
        alarmTitleView = itemView.findViewById(R.id.alarm_title);
        alarmTimeView = itemView.findViewById(R.id.alarm_time);
    }

    public void bind(AlarmEntity alarm, Boolean isSelected) {
        alarmTitleView.setText(alarm.label);
        alarmTimeView.setText(String.format("%02d:%02d", alarm.hour, alarm.minute));
        this.itemView.setAlpha((float) (isSelected ? 0.5 : 1.0));
        Log.d("rcViewSel", "is Selected? " + (isSelected ? "true" : "false") + ", aid: " + alarm.aid + ", position: " + getAdapterPosition());

    }

    static AlarmItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new AlarmItemViewHolder(view);
    }

    public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
        return new ItemDetailsLookup.ItemDetails<Long>() {
            @Override
            public int getPosition() {
                return getAdapterPosition();
            }

            @Nullable
            @Override
            public Long getSelectionKey() {
                return getItemId();
            }
        };
    }
}
