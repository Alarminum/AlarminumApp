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

import com.alarminum.alarminumapp.database.TimerEntity;
import com.alarminum.alarminumapp.databinding.DialogAddTimerBinding;
import com.alarminum.alarminumapp.viewmodel.AddTimerViewModel;

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
                Bundle result = new Bundle();
                result.putInt("group",1);
                getParentFragmentManager().setFragmentResult("add", result);

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
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.timerTitleEdit.setText("");
        binding.timerHourEdit.setText("");
        binding.timerMinuteEdit.setText("");
        binding.timerSecondEdit.setText("");
        binding.timerDisposableCheck.setChecked(false);
        binding.timerVibCheck.setChecked(false);
        binding.timerRingtoneEdit.setText("");
        binding.timerGroupEdit.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
