package com.alarminum.jhtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alarminum.jhtest.view.AlarmDetailsLookup;
import com.alarminum.jhtest.viewmodel.AlarmListViewModel;


public class AlarmFragment extends Fragment {
    private RecyclerView recyclerView;
    private SelectionTracker<Long> selectionTracker;
    private AlarmListViewModel alarmListViewModel;
    private AlarmListAdapter adapter;
    private Menu mainMenu;
    private final int MENU_DELETE = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.mainMenu = menu;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new AlarmListAdapter(new AlarmListAdapter.AlarmDiff());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        setupSelectionTracker();

        adapter.setSelectionTracker(this.selectionTracker);


        alarmListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AlarmListViewModel.class);

        alarmListViewModel.getAllAlarms().observe(getViewLifecycleOwner(), alarmEntities -> {
            adapter.submitList(alarmEntities);
        });


        return view;
    }

    private void setupSelectionTracker() {
        selectionTracker = new SelectionTracker.Builder<>(
                "alarm_id",
                recyclerView,
                new StableIdKeyProvider(recyclerView),
                new AlarmDetailsLookup(recyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectAnything())
                .build();
        addTrackerObserver();
    }

    private void addTrackerObserver() {
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<Long>() {
            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                if(selectionTracker.hasSelection() && (mainMenu.findItem(MENU_DELETE) == null)) {
                    adapter.expandedItemPosition = -1;
                    mainMenu
                        .add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete")
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setOnMenuItemClickListener(item -> {
                            selectionTracker.getSelection().forEach((it)-> {
                                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForItemId(it);
                                if(holder instanceof AlarmItemViewHolder) {
                                    alarmListViewModel.delete(((AlarmItemViewHolder) holder).getElement());
                                }
                            });
                            selectionTracker.clearSelection();
                            return true;
                        })
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                } else if (!selectionTracker.hasSelection() && (mainMenu.findItem(MENU_DELETE) != null)) {
                    mainMenu.removeItem(MENU_DELETE);
                }
            }
        });
    }

    public AlarmListViewModel getViewModel() {
        return alarmListViewModel;
    }


}