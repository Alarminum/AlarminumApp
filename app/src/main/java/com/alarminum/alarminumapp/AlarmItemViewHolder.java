package com.alarminum.alarminumapp;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.databinding.AlarmdetailBinding;


public class AlarmItemViewHolder extends RecyclerView.ViewHolder {
    private AlarmEntity alarm;
    private final AlarmdetailBinding binding;

    public AlarmItemViewHolder(AlarmdetailBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("SetTextI18n")
    public void bind(AlarmEntity alarm) {
        this.alarm = alarm;
        binding.alarmDetailTitle.setText(alarm.label);
        binding.alarmDetailHour.setText(Integer.toString(alarm.hour));
        binding.alarmDetailMinute.setText(Integer.toString(alarm.minute));
        binding.alarmDetailSwitch.setChecked(alarm.activated);
        binding.alarmDetailMonday.setChecked(alarm.weekdayList[0]==1);
        binding.alarmDetailTuesday.setChecked(alarm.weekdayList[1]==1);
        binding.alarmDetailWednesday.setChecked(alarm.weekdayList[2]==1);
        binding.alarmDetailThursday.setChecked(alarm.weekdayList[3]==1);
        binding.alarmDetailFriday.setChecked(alarm.weekdayList[4]==1);
        binding.alarmDetailSaturday.setChecked(alarm.weekdayList[5]==1);
        binding.alarmDetailSunday.setChecked(alarm.weekdayList[6]==1);
        binding.alarmDetailRingtone.setText(alarm.ringtone);
        binding.alarmDetailVib.setChecked(alarm.vibration);
        binding.alarmDetailForOnce.setChecked(alarm.forOnce);

    }

//    static AlarmItemViewHolder create(ViewGroup parent) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recyclerview_item, parent, false);
//        return new AlarmItemViewHolder(binding);
//    }

    public final AlarmEntity getElement() {
        return alarm;
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
