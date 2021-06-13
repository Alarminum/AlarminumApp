package com.alarminum.jhtest;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.alarminum.jhtest.database.TimerEntity;


public class TimerListAdapter extends ListAdapter<TimerEntity, TimerItemViewHolder> {
    public TimerListAdapter(@NonNull DiffUtil.ItemCallback<TimerEntity> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public TimerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TimerItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerItemViewHolder holder, int position) {
        TimerEntity current = getItem(position);
        holder.bind(current);
    }

    static class TimerDiff extends DiffUtil.ItemCallback<TimerEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull TimerEntity oldItem, @NonNull TimerEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TimerEntity oldItem, @NonNull TimerEntity newItem) {
            return oldItem.label.equals(newItem.label);
        }
    }
}
