package com.alarminum.alarminumapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.receivers.AlarmReceiver;
import com.alarminum.alarminumapp.receivers.DeviceBootReceiver;
import com.alarminum.alarminumapp.view.AlarmDetailsLookup;
import com.alarminum.alarminumapp.viewmodel.AlarmListViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class AlarmFragment extends Fragment {
    private RecyclerView recyclerView;
    private SelectionTracker<Long> selectionTracker;
    private AlarmListViewModel alarmListViewModel;
    private AlarmListAdapter adapter;
    private Menu mainMenu;
    private final int MENU_DELETE = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.mainMenu = menu;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new AlarmListAdapter(new AlarmListAdapter.AlarmDiff());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        setupSelectionTracker();

        adapter.setSelectionTracker(this.selectionTracker);


        alarmListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AlarmListViewModel.class);

        alarmListViewModel.getAllAlarms().observe(getViewLifecycleOwner(), alarmEntities -> {
            adapter.submitList(alarmEntities);
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("daily alarm", getActivity().MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);

        Date nextDate = nextNotifyTime.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
        Toast.makeText(getContext().getApplicationContext(),"[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();


        // 이전 설정값으로 TimePicker 초기화
        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        return view;
    }

    private void setupSelectionTracker() {
        selectionTracker = new SelectionTracker.Builder<>(
                "alarm_id",
                recyclerView,
                new StableIdKeyProvider(recyclerView),
                new AlarmDetailsLookup(recyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectAnything())
                .build();
        addTrackerObserver();
    }

    private void addTrackerObserver() {
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<Long>() {
            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                if(selectionTracker.hasSelection() && (mainMenu.findItem(MENU_DELETE) == null)) {
                    adapter.expandedItemPosition = -1;
                    mainMenu
                        .add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete")
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setOnMenuItemClickListener(item -> {
                            selectionTracker.getSelection().forEach((it)-> {
                                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForItemId(it);
                                if(holder instanceof AlarmItemViewHolder) {
                                    alarmListViewModel.delete(((AlarmItemViewHolder) holder).getElement());
                                }
                            });
                            selectionTracker.clearSelection();
                            return true;
                        })
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                } else if (!selectionTracker.hasSelection() && (mainMenu.findItem(MENU_DELETE) != null)) {
                    mainMenu.removeItem(MENU_DELETE);
                }
            }
        });
    }

    public AlarmListViewModel getViewModel() {
        return alarmListViewModel;
    }


}