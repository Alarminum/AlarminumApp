package com.alarminum.alarminumapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.database.AlarmGroup;
import com.alarminum.alarminumapp.repository.AlarmRepository;
import com.alarminum.alarminumapp.repository.GroupRepository;
import com.alarminum.alarminumapp.repository.TimeTableRepository;
import com.alarminum.alarminumapp.utils.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class AddTimeTableViewModel extends ViewModel {
    private final GroupRepository groupRepo;
    private final AlarmRepository alarmRepo;
    private final TimeTableRepository timeTableRepo;

    public AddTimeTableViewModel(Application application) {
        super();
        timeTableRepo = new TimeTableRepository();
        groupRepo = new GroupRepository(application);
        alarmRepo = new AlarmRepository(application);
    }

    public void insertTimeTable(String username, String password) {
        timeTableRepo.makeTimeTableRequest(username, password, result -> {
            if(result instanceof Result.Success) {
                try {
                    JSONObject jsonObject = new JSONObject((Map) ((Result.Success<JSONObject>) result).data);
                    AlarmGroup newTimetable = new AlarmGroup(
                            "2021-1",
                            0,
                            true,
                            null,
                            true,
                            true
                    );
                    long gid = groupRepo.insert(newTimetable);
                    JSONArray subjects = jsonObject.getJSONArray("subject");
                    for(int i=0; i<subjects.length(); i++) {
                        jsonObject = subjects.getJSONObject(i);
                        Integer[] weekdays = new Integer[7];
                        int day1 = jsonObject.getJSONArray("day").getJSONObject(0).getInt("day1");
                        int day2 = jsonObject.getJSONArray("day").getJSONObject(1).getInt("day2");
                        weekdays[day1] = 1;
                        if(day2 == -1) {
                            weekdays[day2] = 1;
                        }
                        AlarmEntity newAlarm = new AlarmEntity(
                                jsonObject.getString("name"),
                                jsonObject.getInt("hour"),
                                jsonObject.getInt("minute"),
                                weekdays,
                                true,
                                null,
                                false,
                                true,
                                (int) gid
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("TimeTableRequest", ((Result.Error)result).exception + " : Time Table request failed");
            }
        });
    }
}
