package com.alarminum.alarminumapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.alarminum.alarminumapp.databinding.SubwayBinding;

public class AddMetroDialog extends DialogFragment {
    private SubwayBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SubwayBinding.inflate(inflater, container, false);

        binding.addSectionBtn.setOnClickListener(v->{

        });
        return binding.getRoot();
    }
}
