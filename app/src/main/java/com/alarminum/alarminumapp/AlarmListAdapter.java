package com.alarminum.alarminumapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.alarminum.alarminumapp.database.AlarmEntity;
import com.alarminum.alarminumapp.databinding.AlarmdetailBinding;
import com.alarminum.alarminumapp.receivers.AlarmReceiver;
import com.alarminum.alarminumapp.receivers.DeviceBootReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class AlarmListAdapter extends ListAdapter<AlarmEntity, AlarmItemViewHolder> {
    private AlarmdetailBinding binding;
    private SelectionTracker<Long> selectionTracker;
    public int expandedItemPosition = -1;

    public AlarmListAdapter(@NonNull DiffUtil.ItemCallback<AlarmEntity> diffCallback) {
        super(diffCallback);
        setHasStableIds(true);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @NonNull
    @Override
    public AlarmItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = AlarmdetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false );
        return new AlarmItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmItemViewHolder holder, int position) {
        AlarmEntity current = getItem(position);
        if(selectionTracker!=null) {
            holder.bind(current);

            boolean isSelected = selectionTracker.isSelected((long) position);
            holder.binding.alarmDetailHeader.setAlpha((float) (isSelected ? 0.5 : 1.0));

            holder.binding.alarmDetailHeader.setOnClickListener(v->{
                if(position == expandedItemPosition) {
                    notifyItemChanged(position);
                    expandedItemPosition = -1;
                } else {
                    if(expandedItemPosition != -1) {
                        notifyItemChanged(expandedItemPosition);
                    }
                    expandedItemPosition = position;
                    notifyItemChanged(position);
                }
            });


            holder.binding.alarmUpdown.setRotation((position == expandedItemPosition) ? 0.0f : 180.0f);
            holder.binding.alarmDetailContainer.setVisibility((position == expandedItemPosition) ? View.VISIBLE : View.GONE);

            holder.binding.alarmDetailSwitch.setOnCheckedChangeListener((v, isChecked)->{
                if(isChecked) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(holder.binding.alarmDetailHour.getText().toString()));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(holder.binding.alarmDetailMinute.getText().toString()));
                    calendar.set(Calendar.SECOND, 0);

                    // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                    if (calendar.before(Calendar.getInstance())) {
                        calendar.add(Calendar.DATE, 1);
                    }

                    Date currentDateTime = calendar.getTime();
                    String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                    Toast.makeText(holder.binding.getRoot().getContext().getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

                    //  Preference에 설정한 값 저장
                    SharedPreferences.Editor editor = holder.binding.getRoot().getContext().getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                    editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                    editor.apply();

                    diaryNotification(calendar);
                }
            });


        }
    }

    static class AlarmDiff extends DiffUtil.ItemCallback<AlarmEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull AlarmEntity oldItem, @NonNull AlarmEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AlarmEntity oldItem, @NonNull AlarmEntity newItem) {
            return oldItem.label.equals(newItem.label);
        }
    }

    void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = binding.getRoot().getContext().getPackageManager();
        ComponentName receiver = new ComponentName(binding.getRoot().getContext(), DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(binding.getRoot().getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(binding.getRoot().getContext(), 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) binding.getRoot().getContext().getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }


}
