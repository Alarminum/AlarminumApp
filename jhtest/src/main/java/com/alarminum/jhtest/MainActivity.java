package com.alarminum.jhtest;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alarminum.jhtest.fragments.AddAlarmDialog;
import com.alarminum.jhtest.fragments.AddTimerDialog;
import com.alarminum.jhtest.viewmodel.GroupListViewModel;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FragmentManager fragmentManager;
    FragmentTransaction ft;
    AlarmFragment alarmFragment;
    TimerFragment timerFragment;

    AddAlarmDialog addAlarmDialog;
    AddTimerDialog addTimerDialog;

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    RecyclerView groupList;
    GroupListAdapter adapter;
    GroupListViewModel groupListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addAlarmDialog = new AddAlarmDialog();
        addTimerDialog = new AddTimerDialog();

        Toolbar mainToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        groupList = findViewById(R.id.group_rcview);
        adapter = new GroupListAdapter(this, new GroupListAdapter.GroupDiff());
        groupList.setLayoutManager(new LinearLayoutManager(this));
        groupList.setAdapter(adapter);

        groupListViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(GroupListViewModel.class);

        groupListViewModel.getAllGroups().observe(this,adapter::submitList);

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
                    addAlarmDialog.show(fragmentManager, "add_alarm_dialog");
//                    Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
//                    startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
                    //Intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    //startActivity(intent);
                    mainSpeedDial.close();
                    break;
                }
                case R.id.fab_add_timer: {
                    addTimerDialog.show(fragmentManager, "add_timer_dialog");
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
    public void onClick(View v) {
        ft = fragmentManager.beginTransaction();

        int id = v.getId();
        switch (id) {
            case R.id.alarm_page_btn:
//                ft.replace(R.id.fragment_container, alarmFragment);
//                ft.commit();
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