package com.alarminum.alarminumapp.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.alarminum.alarminumapp.databinding.EverytimeBinding;
import com.alarminum.alarminumapp.viewmodel.AddTimeTableViewModel;

public class AddTimetableDialog extends DialogFragment {
    private EverytimeBinding binding;
    private AddTimeTableViewModel addTimeTableViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addTimeTableViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AddTimeTableViewModel.class);

        binding = EverytimeBinding.inflate(inflater, container, false);

        binding.loginButton.setOnClickListener(view -> {
            String username = binding.loginId.getText().toString();
            String password = binding.loginPassword.getText().toString();
            addTimeTableViewModel.insertTimeTable(username,password);
            Bundle result = new Bundle();
            result.putInt("group",0);
            getParentFragmentManager().setFragmentResult("add", result);
            dismiss();
        });

        binding.joinButton.setOnClickListener(view -> {
            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.loginId.setText("");
        binding.loginPassword.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
