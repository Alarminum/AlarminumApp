package com.alarminum.alarminumapp.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.databinding.DialogAddAlarmBinding;
import com.alarminum.alarminumapp.viewmodel.AddAlarmViewModel;

public class AddAlarmDialog extends DialogFragment {
    private DialogAddAlarmBinding binding;
    private AddAlarmViewModel addAlarmViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        addAlarmViewModel = new ViewModelProvider(this).get(AddAlarmViewModel.class);

        binding = DialogAddAlarmBinding.inflate(inflater, container, false);

        binding.saveBtn.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.alarmTitleEdit.getText())
                    && !TextUtils.isEmpty(binding.alarmHourEdit.getText())
                    && !TextUtils.isEmpty(binding.alarmMinuteEdit.getText())) {
                Integer[] weekdays = new Integer[7];
                weekdays[0] = binding.mondayCheck.isChecked() ? 1 : 0;
                weekdays[1] = binding.tuesdayCheck.isChecked() ? 1 : 0;
                weekdays[2] = binding.wednesdayCheck.isChecked() ? 1 : 0;
                weekdays[3] = binding.thursdayCheck.isChecked() ? 1 : 0;
                weekdays[4] = binding.fridayCheck.isChecked() ? 1 : 0;
                weekdays[5] = binding.saturdayCheck.isChecked() ? 1 : 0;
                weekdays[6] = binding.sundayCheck.isChecked() ? 1 : 0;
                AlarmEntity newAlarm = new AlarmEntity(
                        binding.alarmTitleEdit.getText().toString(),
                        Integer.parseInt(binding.alarmHourEdit.getText().toString()),
                        Integer.parseInt(binding.alarmMinuteEdit.getText().toString()),
                        weekdays,
                        binding.vibCheck.isChecked(),
                        binding.ringtoneEdit.getText().toString(),
                        binding.alarmDisposableCheck.isChecked(),
                        true, 1
                );

                addAlarmViewModel.insert(newAlarm);
                Bundle result = new Bundle();
                result.putInt("group",0);
                getParentFragmentManager().setFragmentResult("add", result);
            }
            dismissAllowingStateLoss();
        });

        binding.cancelBtn.setOnClickListener(v -> {
            dismissAllowingStateLoss();
        });

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.alarmTitleEdit.setText("");
        binding.alarmHourEdit.setText("");
        binding.alarmMinuteEdit.setText("");
        binding.mondayCheck.setChecked(false);
        binding.tuesdayCheck.setChecked(false);
        binding.wednesdayCheck.setChecked(false);
        binding.thursdayCheck.setChecked(false);
        binding.fridayCheck.setChecked(false);
        binding.saturdayCheck.setChecked(false);
        binding.sundayCheck.setChecked(false);
        binding.alarmDisposableCheck.setChecked(false);
        binding.vibCheck.setChecked(false);
        binding.ringtoneEdit.setText("");
        binding.alarmGroupEdit.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
