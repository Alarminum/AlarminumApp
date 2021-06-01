package com.alarminum.alarminumapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.database.TimerEntity;
import com.alarminum.alarminumapp.viewmodel.AlarmViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Fragment currentFragment =  fragmentManager.findFragmentById(R.id.fragment_container);
            Intent intent;
            if(currentFragment instanceof AlarmFragment) {
                intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
//                intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
//                startActivity(intent);
            } else if (currentFragment instanceof TimerFragment) {
                intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivityForResult(intent, NEW_TIMER_ACTIVITY_REQUEST_CODE);
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