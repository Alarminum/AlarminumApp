package com.alarminum.alarminumapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.alarminumapp.database.TimerEntity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class TimerViewHolder extends RecyclerView.ViewHolder {
    private final TextView timerTitleView;
    private final TextView timerTimeView;
    private final Switch timerDetailSwitch;

    private TimerViewHolder(View itemView) {
        super(itemView);
        timerTitleView = itemView.findViewById(R.id.timer_title);
        timerTimeView = itemView.findViewById(R.id.timer_time);
        timerDetailSwitch = itemView.findViewById(R.id.timer_detail_switch);
    }

    public void bind(TimerEntity timer) {
        timerTitleView.setText(timer.label);
        timerTimeView.setText(String.format("%02d:%02d:%02d", timer.hour, timer.minute,timer.second));

        timerDetailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final int[] hour = {Integer.parseInt(Integer.toString(timer.hour))};
            final int[] minute = {Integer.parseInt(Integer.toString(timer.minute))};
            final int[] second = {Integer.parseInt(Integer.toString(timer.second))};

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                Ringtone rt = RingtoneManager.getRingtone(itemView.getContext().getApplicationContext(),notification);
                Log.d("timer", "switch checked " + b);

                if(b) {
                    Timer tmr = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {

                            // 반복실행할 구문

                            // 0초 이상이면
                            if(second[0] != 0) {
                                //1초씩 감소
                                second[0]--;

                                // 0분 이상이면
                            } else if(minute[0] != 0) {
                                // 1분 = 60초
                                second[0] = 60;
                                second[0]--;
                                minute[0]--;

                                // 0시간 이상이면
                            } else if(hour[0] != 0) {
                                // 1시간 = 60분
                                second[0] = 60;
                                minute[0] = 60;
                                second[0]--;
                                minute[0]--;
                                hour[0]--;
                            }
                            // 시분초가 다 0이라면 toast를 띄우고 타이머를 종료한다..
                            Log.d("timer", hour[0] +" : "+ minute[0] +" : "+ second[0]);

                            if(hour[0] == 0 && minute[0] == 0 && second[0] == 0) {

                                tmr.cancel();//타이머 종료

                                itemView.getContext().startActivity(new Intent(itemView.getContext(), MainActivity.class));
                                //Toast.makeText(itemView.getContext().getApplicationContext(), "타이머가 종료되었습니다", Toast.LENGTH_SHORT ).show();
                                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                                Ringtone rt = RingtoneManager.getRingtone(itemView.getContext().getApplicationContext(),notification);
                                rt.play();
                            }
                        }
                    };

                    //타이머를 실행
                    tmr.schedule(timerTask, 0, 1000); //Timer 실행

                } else {
                    rt.stop();
                }
            }
        });
    }

    static TimerViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timer_item, parent, false);
        return new TimerViewHolder(view);
    }
}
