package com.alarminum.jhtest;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.alarminum.jhtest.database.AlarmGroup;


public class GroupListAdapter extends ListAdapter<AlarmGroup, GroupViewHolder> {
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private final AppCompatActivity activity;
    AlarmFragment alarmFragment;
    TimerFragment timerFragment;

    public GroupListAdapter( Context context, DiffUtil.ItemCallback<AlarmGroup> diffCallback) {
        super(diffCallback);
        this.activity = (AppCompatActivity) context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        alarmFragment = new AlarmFragment();
        timerFragment = new TimerFragment();
        fragmentManager = activity.getSupportFragmentManager();

        return GroupViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        AlarmGroup current = getItem(position);
        holder.bind(current);
        holder.itemView.setOnClickListener(v->{
            ft = fragmentManager.beginTransaction();

            int id = current.groupType;
            switch (id) {
                case 0:
                    ft.replace(R.id.fragment_container, alarmFragment);
                    ft.commit();
                    break;
                case 1:
                    ft.replace(R.id.fragment_container, timerFragment);
                    ft.commit();
                    break;
            }
        });
    }

    static class GroupDiff extends DiffUtil.ItemCallback<AlarmGroup> {
        @Override
        public boolean areItemsTheSame(@NonNull  AlarmGroup oldItem, @NonNull  AlarmGroup newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AlarmGroup oldItem, @NonNull AlarmGroup newItem) {
            return oldItem.label.equals(newItem.label);
        }
    }
}
