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

import com.alarminum.jhtest.database.TimerEntity;
import com.alarminum.jhtest.databinding.DialogAddTimerBinding;
import com.alarminum.jhtest.viewmodel.AddTimerViewModel;

public class AddTimerDialog extends DialogFragment {
    private DialogAddTimerBinding binding;
    private AddTimerViewModel addTimerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        addTimerViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AddTimerViewModel.class);

        binding = DialogAddTimerBinding.inflate(inflater, container, false);

        binding.saveTimerBtn.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.timerTitleEdit.getText())
                    && !TextUtils.isEmpty(binding.timerHourEdit.getText())
                    && !TextUtils.isEmpty(binding.timerMinuteEdit.getText())
                    && !TextUtils.isEmpty(binding.timerSecondEdit.getText())) {
                TimerEntity newTimer = new TimerEntity(
                        binding.timerTitleEdit.getText().toString(),
                        Integer.parseInt(binding.timerHourEdit.getText().toString()),
                        Integer.parseInt(binding.timerMinuteEdit.getText().toString()),
                        Integer.parseInt(binding.timerSecondEdit.getText().toString()),
                        binding.timerVibCheck.isChecked(),
                        binding.timerRingtoneEdit.getText().toString(),
                        binding.timerDisposableCheck.isChecked(),
                        true, 1
                );

                addTimerViewModel.insert(newTimer);
            }
            dismiss();
        });

        binding.cancelTimerBtn.setOnClickListener(v -> {
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
