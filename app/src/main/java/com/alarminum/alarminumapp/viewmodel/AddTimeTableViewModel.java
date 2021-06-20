package com.alarminum.alarminumapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.database.AlarmGroup;
import com.alarminum.alarminumapp.repository.AlarmRepository;
import com.alarminum.alarminumapp.repository.GroupRepository;
import com.alarminum.alarminumapp.repository.TimeTableRepository;
import com.alarminum.alarminumapp.utils.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class AddTimeTableViewModel extends AndroidViewModel {
    private final GroupRepository groupRepo;
    private final AlarmRepository alarmRepo;
    private final TimeTableRepository timeTableRepo;

    public AddTimeTableViewModel(Application application) {
        super(application);
        timeTableRepo = new TimeTableRepository();
        groupRepo = new GroupRepository(application);
        alarmRepo = new AlarmRepository(application);
    }

    public void insertTimeTable(String username, String password) {
        timeTableRepo.makeTimeTableRequest(username, password, result -> {
            if(result instanceof Result.Success) {
                try {
                    JSONObject jsonObject = ((Result.Success<JSONObject>) result).data;
                    AlarmGroup newTimetable = new AlarmGroup(
                            "2021-1",
                            0,
                            true,
                            null,
                            true,
                            true
                    );
                    groupRepo.insert(newTimetable);
                    int gid = groupRepo.getLatestGid();
                    Log.d("timetable", "new gid: "+gid);
                    JSONArray subjects = jsonObject.getJSONArray("subject");
                    for(int i=0; i<subjects.length(); i++) {
                        jsonObject = subjects.getJSONObject(i);
                        Integer[] weekdays = {0,0,0,0,0,0,0};
                        int day1 = jsonObject.getJSONArray("day").getJSONObject(0).getInt("day1");
                        int day2 = jsonObject.getJSONArray("day").getJSONObject(1).getInt("day2");
                        weekdays[day1] = 1;
                        if(day2 != -1) {
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
                        alarmRepo.insert(newAlarm);
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
