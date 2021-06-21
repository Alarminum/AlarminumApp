package com.alarminum.alarminumapp.repository;

import android.util.Log;


import androidx.annotation.NonNull;

import com.alarminum.alarminumapp.utils.AppExecutors;
import com.alarminum.alarminumapp.utils.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MetroFirestoreRepository {
    ArrayList<String> startLine = new ArrayList<>(2);
    ArrayList<String> startStation = new ArrayList<>(2);
    ArrayList<String> endStation = new ArrayList<>(2);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference subway = db.collection("subway");

    public void makeMetroTravelTimeRequest(int startCode1, int endCode1, int startCode2, int endCode2, final RepositoryCallback<Integer> callback) {
        AppExecutors.getInstance().getNetworkIO().execute(() -> {
                    try {
                        makeSyncMetroTravelTimeRequest(startCode1, endCode1, startCode2, endCode2, r -> {
                            callback.onComplete(r);
                        });
                    } catch (Exception e) {
                        Result<Integer> errorResult = new Result.Error<>(e);
                        callback.onComplete(errorResult);
                    }
                }
        );
    }

        private void makeSyncMetroTravelTimeRequest ( int startCode1, int endCode1, int startCode2, int endCode2, final RepositoryCallback<Integer> callback) {
            Integer[] start = new Integer[2];
            Integer[] end = new Integer[2];
            start[0] = startCode1;
            start[1] = startCode2;

            if (startCode2 != 0) {
                end[0] = endCode1;
                end[1] = endCode2;
            }

            Integer[] resultIntervals = new Integer[2];

            int depart1 = min(endCode1, startCode1);
            int arrive1 = max(endCode1, startCode1);

            subway.whereGreaterThanOrEqualTo("stationcode", depart1)
                    .whereLessThanOrEqualTo("stationcode", arrive1)
                    .get()
                    .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                int interval1 = 0;
                                for (QueryDocumentSnapshot ds : task.getResult()) {
                                    Integer interval_temp = Integer.parseInt(ds.get("interval").toString());
                                     interval1 = interval1 + interval_temp;
                                    Log.d("intev", "new interval : " + interval_temp);
                                }
                                resultIntervals[0] = interval1;
                                if(startCode1 != 0) {
                                    int depart2 = min(endCode2, startCode2);
                                    int arrive2 = max(endCode2, startCode2);
                                    subway.whereGreaterThanOrEqualTo("stationcode", depart2)
                                            .whereLessThanOrEqualTo("stationcode", arrive2)
                                            .get()
                                            .addOnCompleteListener(task2 -> {
                                                Integer interval2 = 0; //최종 시간
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot ds : task.getResult()) {
                                                            Integer interval_temp = Integer.parseInt(ds.get("interval").toString());
                                                            interval2 = interval2 + interval_temp;
                                                            Log.d("intev", "new interval : " + interval_temp);
                                                        }
                                                        resultIntervals[1] = interval2;
                                                    }

                                            });
                                }
                            }

                    });


        }

}
