package com.alarminum.jhtest;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.alarminum.jhtest.database.AlarmEntity;


public class AlarmListAdapter extends ListAdapter<AlarmEntity, AlarmItemViewHolder> {
    private SelectionTracker<Long> selectionTracker;
    public int expandedItemPosition = -1;

    public AlarmListAdapter(@NonNull DiffUtil.ItemCallback<AlarmEntity> diffCallback) {
        super(diffCallback);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @NonNull
    @Override
    public AlarmItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AlarmItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmItemViewHolder holder, int position) {
        AlarmEntity current = getItem(position);
        if(selectionTracker!=null) {
            holder.bind(current);

            boolean isSelected = selectionTracker.isSelected((long) position);
            holder.itemView.setAlpha((float) (isSelected ? 0.5 : 1.0));

            holder.itemView.setOnClickListener(v->{
                if(position == expandedItemPosition) {
                    notifyItemChanged(position);
                    expandedItemPosition = -1;
                } else {
                    if(expandedItemPosition != -1) {
                        notifyItemChanged(expandedItemPosition);
                    }
                    expandedItemPosition = position;
                    notifyItemChanged(position);
                }
            });

            holder.alarmTitleView.setVisibility((position == expandedItemPosition) ? View.VISIBLE : View.GONE);
        }
    }

    static class AlarmDiff extends DiffUtil.ItemCallback<AlarmEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull AlarmEntity oldItem, @NonNull AlarmEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AlarmEntity oldItem, @NonNull AlarmEntity newItem) {
            return oldItem.label.equals(newItem.label);
        }
    }
}
