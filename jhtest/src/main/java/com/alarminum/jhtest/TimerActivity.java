 package com.alarminum.jhtest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alarminum.jhtest.database.TimerEntity;


 public class TimerActivity extends AppCompatActivity {
     public static final String EXTRA_REPLY = "REPLY";
     private EditText aTitleEditText;
     private EditText aHourEditText;
     private EditText aMinuteEditText;
     private EditText aSecondEditText;
     private CheckBox aDisposableCheckbox;
     private EditText aRepeatTimeEditText;
     private CheckBox aVibCheckbox;
     private EditText aRingtoneEditText;
     private EditText aGroupNameEditText;

     private TimerEntity newTimer;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_timer);

         final Button saveButton = findViewById(R.id.save_timer_btn);
         final Button cancelButton = findViewById(R.id.cancel_timer_btn);

         aTitleEditText =  findViewById(R.id.timer_title_edit);
         aHourEditText= findViewById(R.id.timer_hour_edit);
         aMinuteEditText =  findViewById(R.id.timer_minute_edit);
         aSecondEditText =  findViewById(R.id.timer_second_edit);
         aDisposableCheckbox = findViewById(R.id.timer_disposable_check);

         aRepeatTimeEditText = findViewById(R.id.timer_repeat_time_edit);
         aVibCheckbox = findViewById(R.id.timer_vib_check);
         aRingtoneEditText = findViewById(R.id.timer_ringtone_edit);
         aGroupNameEditText = findViewById(R.id.timer_group_edit);

         saveButton.setOnClickListener(view -> {
             Intent replyIntent = new Intent();
             if(TextUtils.isEmpty(aTitleEditText.getText())) {
                 setResult(RESULT_CANCELED, replyIntent);
             } else {
                 String title = aTitleEditText.getText().toString();
                 int hour = Integer.parseInt(aHourEditText.getText().toString());
                 int minute = Integer.parseInt(aMinuteEditText.getText().toString());
                 int second = Integer.parseInt(aSecondEditText.getText().toString());

                 int isDisposable = aDisposableCheckbox.isChecked() ? 1: 0;
                 int repeatTime = Integer.parseInt(aRepeatTimeEditText.getText().toString());
                 int willVibe = aVibCheckbox.isChecked() ? 1: 0;
                 String ringtone = aRingtoneEditText.getText().toString();
                 String groupName = aGroupNameEditText.getText().toString();
                 newTimer = new TimerEntity(title, hour, minute, second, repeatTime, willVibe, ringtone, isDisposable, 1, 0);

                 replyIntent.putExtra(EXTRA_REPLY, newTimer);
                 setResult(RESULT_OK, replyIntent);
             }
             finish();
         });

     }
 }