package com.alarminum.alarminumapp.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.ViewGroup;

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
                final JSONObject[] jsonObject = {((Result.Success<JSONObject>) result).data};
                AlarmGroup newTimetable = new AlarmGroup(
                        "2021-1",
                        0,
                        true,
                        null,
                        true,
                        true
                );
                groupRepo.insert(newTimetable);
                groupRepo.getLatestGid(r -> {
                    try {
                        JSONArray subjects = jsonObject[0].getJSONArray("subject");
                        for(int i=0; i<subjects.length(); i++) {
                            jsonObject[0] = subjects.getJSONObject(i);
                            Integer[] weekdays = {0,0,0,0,0,0,0};
                            int day1 = jsonObject[0].getJSONArray("day").getJSONObject(0).getInt("day1");
                            int day2 = jsonObject[0].getJSONArray("day").getJSONObject(1).getInt("day2");
                            weekdays[day1] = 1;
                            if(day2 != -1) {
                                weekdays[day2] = 1;
                            }
                            AlarmEntity newAlarm = new AlarmEntity(
                                    jsonObject[0].getString("name"),
                                    jsonObject[0].getInt("hour"),
                                    jsonObject[0].getInt("minute"),
                                    weekdays,
                                    true,
                                    null,
                                    false,
                                    true,
                                    (int) ((Result.Success<Integer>)r).data
                            );
                            alarmRepo.insert(newAlarm);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                Log.d("TimeTableRequest", ((Result.Error)result).exception + " : Time Table request failed");
            }
        });
    }
}
