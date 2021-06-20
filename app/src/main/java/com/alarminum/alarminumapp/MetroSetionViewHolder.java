package com.alarminum.alarminumapp;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.databinding.SubwayBinding;

public class MetroSetionViewHolder extends RecyclerView.ViewHolder {
    private SubwayBinding binding;

    public MetroSetionViewHolder(SubwayBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
