package com.alarminum.jhtest;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.jhtest.database.AlarmEntity;
import com.alarminum.jhtest.database.AlarmGroup;

import java.util.List;

public class GroupListAdapter extends ListAdapter<AlarmGroup, GroupViewHolder> {
    public GroupListAdapter(DiffUtil.ItemCallback<AlarmGroup> diffCallback) {
        super(diffCallback);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return GroupViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        AlarmGroup current = getItem(position);
        holder.bind(current);
    }

    static class GroupDiff extends DiffUtil.ItemCallback<AlarmGroup> {
        @Override
        public boolean areItemsTheSame(@NonNull  AlarmGroup oldItem, @NonNull  AlarmGroup newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AlarmGroup oldItem, @NonNull AlarmGroup newItem) {
            return oldItem.label.equals(newItem.label);
        }
    }
}
