package com.alarminum.alarminumapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.database.AlarmGroup;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    private AlarmGroup group;
    private final TextView groupTitleView;
    private final TextView groupNumberView;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        groupTitleView = itemView.findViewById(R.id.group_title);
        groupNumberView = itemView.findViewById(R.id.group_number);
    }

    public void bind(AlarmGroup group) {
        this.group = group;
        groupTitleView.setText(group.label);
        groupNumberView.setText(String.valueOf(group.gid));
    }

    static GroupViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(view);
    }

    public final AlarmGroup getElement() {return group;}
}
