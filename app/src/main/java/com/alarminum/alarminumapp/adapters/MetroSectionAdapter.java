package com.alarminum.alarminumapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.MetroSetionViewHolder;
import com.alarminum.alarminumapp.databinding.SubwayBinding;

import java.util.ArrayList;
import java.util.List;

public class MetroSectionAdapter extends RecyclerView.Adapter<MetroSetionViewHolder> {
    private SubwayBinding binding;


    @NonNull
    @Override
    public MetroSetionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = SubwayBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MetroSetionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MetroSetionViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull MetroSetionViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
