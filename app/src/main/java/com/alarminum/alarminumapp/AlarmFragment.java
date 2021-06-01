package com.alarminum.alarminumapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.viewmodel.AlarmViewModel;


public class AlarmFragment extends Fragment {
    private RecyclerView recyclerView;
    private AlarmViewModel alarmViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        final AlarmListAdapter adapter = new AlarmListAdapter(new AlarmListAdapter.AlarmDiff());
        recyclerView.setLayoutManager(new
                LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        alarmViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AlarmViewModel.class);

        alarmViewModel.getAllAlarms().observe(this, alarmEntities -> {
            adapter.submitList(alarmEntities);
        });


        return view;
    }

    public AlarmViewModel getViewModel() {
        return alarmViewModel;
    }
}