package com.alarminum.jhtest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alarminum.jhtest.database.AlarmEntity;


public class AlarmActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "REPLY";
    private EditText aTitleEditText;
    private EditText aHourEditText;
    private EditText aMinuteEditText;
    private CheckBox aDisposableCheckbox;
    private CheckBox aMonCheckbox;
    private CheckBox aTueCheckbox;
    private CheckBox aWedCheckbox;
    private CheckBox aThuCheckbox;
    private CheckBox aFriCheckbox;
    private CheckBox aSatCheckbox;
    private CheckBox aSunCheckbox;
    private CheckBox aVibCheckbox;
    private EditText aRingtoneEditText;
    private EditText aGroupNameEditText;

    private AlarmEntity newAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        final Button saveButton = findViewById(R.id.save_btn);
        final Button cancelButton = findViewById(R.id.cancel_btn);

        aTitleEditText =  findViewById(R.id.alarm_title_edit);
        aHourEditText= findViewById(R.id.alarm_hour_edit);
        aMinuteEditText =  findViewById(R.id.alarm_minute_edit);
        aDisposableCheckbox = findViewById(R.id.alarm_disposable_check);
        aMonCheckbox = findViewById(R.id.monday_check);
        aTueCheckbox = findViewById(R.id.tuesday_check);
        aWedCheckbox = findViewById(R.id.wednesday_check);
        aThuCheckbox = findViewById(R.id.thursday_check);
        aFriCheckbox = findViewById(R.id.friday_check);
        aSatCheckbox = findViewById(R.id.saturday_check);
        aSunCheckbox = findViewById(R.id.sunday_check);
        aVibCheckbox = findViewById(R.id.vib_check);
        aRingtoneEditText = findViewById(R.id.ringtone_edit);
        aGroupNameEditText = findViewById(R.id.alarm_group_edit);

        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if(TextUtils.isEmpty(aTitleEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String title = aTitleEditText.getText().toString();
                int hour = Integer.parseInt(aHourEditText.getText().toString());
                int minute = Integer.parseInt(aMinuteEditText.getText().toString());
                int isDisposable = aDisposableCheckbox.isChecked() ? 1: 0;
                Integer[] weekdayList = new Integer[7];
                int containMonday = aMonCheckbox.isChecked() ? 1: 0;
                int containTuesday = aTueCheckbox.isChecked() ? 1: 0;
                int containWednesday = aWedCheckbox.isChecked() ? 1: 0;
                int containThursday = aThuCheckbox.isChecked() ? 1: 0;
                int containFriday = aFriCheckbox.isChecked() ? 1: 0;
                int containSaturday = aSatCheckbox.isChecked() ? 1: 0;
                int containSunday = aSunCheckbox.isChecked() ? 1: 0;
                weekdayList[0] = containMonday;
                weekdayList[1] = containTuesday;
                weekdayList[2] = containWednesday;
                weekdayList[3] = containThursday;
                weekdayList[4] = containFriday;
                weekdayList[5] = containSaturday;
                weekdayList[6] = containSunday;
                int willVibe = aVibCheckbox.isChecked() ? 1: 0;
                String ringtone = aRingtoneEditText.getText().toString();
                String groupName = aGroupNameEditText.getText().toString();
                newAlarm = new AlarmEntity(title, hour, minute, weekdayList, willVibe, ringtone, isDisposable, 1, 1);

                replyIntent.putExtra(EXTRA_REPLY, newAlarm);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

    }
}