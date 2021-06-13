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

import com.alarminum.jhtest.viewmodel.TimerListViewModel;


public class TimerFragment extends Fragment {
    private RecyclerView recyclerView;
    private TimerListViewModel timerViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        recyclerView = view.findViewById(R.id.timer_rc_view);
        final TimerListAdapter adapter = new TimerListAdapter(new TimerListAdapter.TimerDiff());
        recyclerView.setLayoutManager(new
                LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        timerViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(TimerListViewModel.class);

        timerViewModel.getAllTimers().observe(getViewLifecycleOwner(), timerEntities -> {
            adapter.submitList(timerEntities);
        });

        return view;
    }

    public TimerListViewModel getViewModel() {
        return timerViewModel;
    }
}