package com.alarminum.jhtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.alarminum.jhtest.database.AlarmEntity;
import com.alarminum.jhtest.database.TimerEntity;
import com.alarminum.jhtest.viewmodel.AlarmListViewModel;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FragmentManager fragmentManager;
    FragmentTransaction ft;
    AlarmFragment alarmFragment;
    TimerFragment timerFragment;

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    public static final int NEW_ALARM_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_TIMER_ACTIVITY_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mainToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


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
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white))
                .create());
        mainSpeedDial.addActionItem(new SpeedDialActionItem
                .Builder(R.id.fab_add_timer, R.drawable.ic_timer_24)
                .setLabel("타이머")
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white))
                .create());
        mainSpeedDial.addActionItem(new SpeedDialActionItem
                .Builder(R.id.fab_add_timetable, R.drawable.ic_timetable_24)
                .setLabel("시간표")
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white))
                .create());
        mainSpeedDial.addActionItem(new SpeedDialActionItem
                .Builder(R.id.fab_add_metro, R.drawable.ic_baseline_directions_subway_24)
                .setLabel("지하철")
                .setFabBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white))
                .create());

        mainSpeedDial.setOnActionSelectedListener(actionItem -> {
            switch (actionItem.getId()) {
                case R.id.fab_add_alarm: {
                    Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                    startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
                    //Intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    //startActivity(intent);
                    mainSpeedDial.close();
                    break;
                }
                case R.id.fab_add_timer: {
                    Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                    startActivityForResult(intent, NEW_TIMER_ACTIVITY_REQUEST_CODE);
                    mainSpeedDial.close();
                    break;
                }
                default: {
                    mainSpeedDial.close();
                    break;
                }
            }
            return true;
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
                timerFragment.getViewModel().insert(timerEntity);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}