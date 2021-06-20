package com.alarminum.alarminumapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.databinding.AlarmdetailBinding;


public class AlarmListAdapter extends ListAdapter<AlarmEntity, AlarmItemViewHolder> {
    private AlarmdetailBinding binding;
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @NonNull
    @Override
    public AlarmItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = AlarmdetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false );
        return new AlarmItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmItemViewHolder holder, int position) {
        AlarmEntity current = getItem(position);
        if(selectionTracker!=null) {
            holder.bind(current);

            boolean isSelected = selectionTracker.isSelected((long) position);
            binding.getRoot().setAlpha((float) (isSelected ? 0.5 : 1.0));

            binding.alarmDetailHeader.setOnClickListener(v->{
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
            binding.alarmUpdown.setRotation((position == expandedItemPosition) ? 0.0f : 180.0f);
            binding.alarmDetailContainer.setVisibility((position == expandedItemPosition) ? View.VISIBLE : View.GONE);
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
