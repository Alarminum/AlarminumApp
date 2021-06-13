package com.alarminum.jhtest;

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

import com.alarminum.jhtest.viewmodel.AlarmListViewModel;


public class AlarmFragment extends Fragment {
    private RecyclerView recyclerView;
    private AlarmListViewModel alarmListViewModel;

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

        alarmListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AlarmListViewModel.class);

        alarmListViewModel.getAllAlarms().observe(getViewLifecycleOwner(), alarmEntities -> {
            adapter.submitList(alarmEntities);
        });


        return view;
    }

    public AlarmListViewModel getViewModel() {
        return alarmListViewModel;
    }
}