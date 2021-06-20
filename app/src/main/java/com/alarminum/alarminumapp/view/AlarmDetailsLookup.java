package com.alarminum.alarminumapp.view;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.AlarmItemViewHolder;

public class AlarmDetailsLookup extends ItemDetailsLookup<Long> {
    private RecyclerView recyclerView;
    public AlarmDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if(view != null) {
            AlarmItemViewHolder viewHolder = (AlarmItemViewHolder) recyclerView.getChildViewHolder(view);
            return viewHolder.getItemDetails();
        }

        return null;
    }
}

