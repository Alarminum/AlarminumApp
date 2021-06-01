package com.alarminum.alarminumapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.database.TimerEntity;
import com.alarminum.alarminumapp.viewmodel.AlarmViewModel;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AlarmViewModel alarmViewModel;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    AlarmFragment alarmFragment;
    TimerFragment timerFragment;

    public static final int NEW_ALARM_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_TIMER_ACTIVITY_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        Button alarmPageButton = findViewById(R.id.alarm_page_btn);
        Button timerPageButton = findViewById(R.id.timer_page_btn);

        alarmFragment = new AlarmFragment();
        timerFragment = new TimerFragment();

        ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, alarmFragment);
        ft.addToBackStack(null);
        ft.commit();

        alarmPageButton.setOnClickListener(this);
        timerPageButton.setOnClickListener(this);

        SpeedDialView mainSpeedDial = findViewById(R.id.mainSD);
        mainSpeedDial.addActionItem(new SpeedDialActionItem
                .Builder(R.id.fab_add_alarm, R.drawable.ic_alarm_24)
                .setLabel("알람")
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.teal_700))
                .create());
        mainSpeedDial.addActionItem(new SpeedDialActionItem
                .Builder(R.id.fab_add_timer, R.drawable.ic_timer_24)
                .setLabel("타이머")
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.teal_700))
                .create());
        mainSpeedDial.addActionItem(new SpeedDialActionItem
                .Builder(R.id.fab_add_timetable, R.drawable.ic_timetable_24)
                .setLabel("시간표")
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.teal_700))
                .create());
        mainSpeedDial.addActionItem(new SpeedDialActionItem
                .Builder(R.id.fab_add_metro, R.drawable.ic_baseline_directions_subway_24)
                .setLabel("지하철")
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.teal_700))
                .create());

        mainSpeedDial.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.fab_add_alarm: {
                        Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                        startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
                        //Intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                        //startActivity(intent);
                        break;
                    }

                    case R.id.fab_add_timer: {
                        Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                        startActivityForResult(intent, NEW_TIMER_ACTIVITY_REQUEST_CODE);
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == NEW_ALARM_ACTIVITY_REQUEST_CODE ) {
                AlarmEntity alarmEntity = (AlarmEntity) data.getSerializableExtra(AlarmActivity.EXTRA_REPLY);
                alarmFragment.getViewModel().insert(alarmEntity);
            } else if(requestCode == NEW_TIMER_ACTIVITY_REQUEST_CODE) {
                TimerEntity timerEntity = (TimerEntity) data.getSerializableExtra(TimerActivity.EXTRA_REPLY);
                alarmFragment.getViewModel().insert(timerEntity);
            }

            Toast.makeText(
                    getApplicationContext(),
                    R.string.saved,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        ft = fragmentManager.beginTransaction();

        int id = v.getId();
        switch (id) {
            case R.id.alarm_page_btn:
                ft.replace(R.id.fragment_container, alarmFragment);
                ft.commit();
                break;
            case R.id.timer_page_btn:
                ft.replace(R.id.fragment_container, timerFragment);
                ft.commit();
                break;
        }
    }
}