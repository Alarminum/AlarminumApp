package com.alarminum.jhtest.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.alarminum.jhtest.database.AlarmEntity;
import com.alarminum.jhtest.databinding.DialogAddAlarmBinding;
import com.alarminum.jhtest.viewmodel.AddAlarmViewModel;

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
        addAlarmViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AddAlarmViewModel.class);

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
            }
            dismiss();
        });

        binding.cancelBtn.setOnClickListener(v -> {
            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
