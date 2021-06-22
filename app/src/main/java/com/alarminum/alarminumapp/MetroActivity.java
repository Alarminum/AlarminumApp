package com.alarminum.alarminumapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.alarminum.alarminumapp.R;
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


public class MetroActivity extends AppCompatActivity {
    ArrayList<String> startLine = new ArrayList<>(2);
    ArrayList<String> startStation = new ArrayList<>(2);
    ArrayList<String> endStation = new ArrayList<>(2);
    ArrayAdapter<CharSequence> adspin1, adspin2, adspin3;
    ArrayAdapter<CharSequence> adspin4, adspin5, adspin6;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference subway = db.collection("subway");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);
        final Spinner spin1 = (Spinner)findViewById(R.id.spinner);
        final Spinner spin2 = (Spinner)findViewById(R.id.spinner2);
        final Spinner spin3 = (Spinner)findViewById(R.id.spinner3);
        final Spinner spin4 = (Spinner)findViewById(R.id.spinner4);
        final Spinner spin5 = (Spinner)findViewById(R.id.spinner5);
        final Spinner spin6 = (Spinner)findViewById(R.id.spinner6);
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        Button btn_refresh = (Button)findViewById(R.id.btn_refresh);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.spinner_line, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (adspin1.getItem(i).equals("1호선")) {
                    startLine.add("1호선");
                    adspin2 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line1, android.R.layout.simple_spinner_dropdown_item);
                    adspin3 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line1, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin3.setAdapter(adspin3);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                            startStation.add(adspin2.getItem(i).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            endStation.add(adspin3.getItem(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } else if (adspin1.getItem(i).equals("2호선")) {
                    startLine.add("2호선");
                    adspin2 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                    adspin3 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin3.setAdapter(adspin3);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                            startStation.add(adspin2.getItem(i).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            endStation.add(adspin3.getItem(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } else if (adspin1.getItem(i).equals("3호선")) {
                    startLine.add("3호선");
                    adspin2 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                    adspin3 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin3.setAdapter(adspin3);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                            startStation.add(adspin2.getItem(i).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            endStation.add(adspin3.getItem(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } else {
                    startLine.add("4호선");
                    adspin2 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                    adspin3 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin3.setAdapter(adspin3);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                            startStation.add(adspin2.getItem(i).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            endStation.add(adspin3.getItem(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        checkBox.setOnCheckedChangeListener((v, isChecked) -> {
            if(isChecked){
                adspin4 = ArrayAdapter.createFromResource(this, R.array.spinner_line, android.R.layout.simple_spinner_dropdown_item);
                adspin4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin4.setAdapter(adspin4);
                spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                        if (adspin4.getItem(i).equals("1호선")) {
                            startLine.add("1호선");
                            adspin5 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line1, android.R.layout.simple_spinner_dropdown_item);
                            adspin6 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line1, android.R.layout.simple_spinner_dropdown_item);
                            adspin5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            adspin6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin5.setAdapter(adspin5);
                            spin6.setAdapter(adspin6);
                            spin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                    startStation.add(adspin5.getItem(i).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            spin6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    endStation.add(adspin6.getItem(position).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        } else if (adspin4.getItem(i).equals("2호선")) {
                            startLine.add("2호선");
                            adspin5 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                            adspin6 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                            adspin5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            adspin6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin5.setAdapter(adspin5);
                            spin6.setAdapter(adspin6);
                            spin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                    startStation.add(adspin5.getItem(i).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            spin6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    endStation.add(adspin6.getItem(position).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        } else if (adspin4.getItem(i).equals("3호선")) {
                            startLine.add("3호선");
                            adspin5 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                            adspin6 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                            adspin5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            adspin6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin5.setAdapter(adspin5);
                            spin6.setAdapter(adspin6);
                            spin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                    startStation.add(adspin5.getItem(i).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            spin6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    endStation.add(adspin6.getItem(position).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        } else {
                            startLine.add("4호선");
                            adspin5 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                            adspin6 = ArrayAdapter.createFromResource(MetroActivity.this, R.array.spinner_line2, android.R.layout.simple_spinner_dropdown_item);
                            adspin5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            adspin6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin5.setAdapter(adspin5);
                            spin6.setAdapter(adspin6);
                            spin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                    startLine.add(adspin5.getItem(i).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            spin6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    endStation.add(adspin6.getItem(position).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }});
            }

        });



        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이 아래는 필요 없음.. 그냥 한겨~ 두번째 꺼만 필요함
                int count=1;
                if(checkBox.isChecked()) count++;
                ArrayList<Integer> Result = new ArrayList<Integer>(2);
                Integer[] start = new Integer[1];
                Integer[] end = new Integer[1];

                for (int i = 0; i < count; ++i) {
                    int finalI = i;
                    subway.whereEqualTo("stationname", startStation.get(i))
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                                        start[0] = Integer.parseInt(ds.get("stationcode").toString());
                                    }
                                    subway.whereEqualTo("stationname", endStation.get(finalI))
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                                                        end[0] = Integer.parseInt(ds.get("stationcode").toString());
                                                    }
                                                    int depart = min(end[0],start[0]);
                                                    int arrive = max(end[0],start[0]);

                                                    subway.whereGreaterThanOrEqualTo("stationcode", depart)
                                                            .whereLessThanOrEqualTo("stationcode", arrive)
                                                            .get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    Integer interval = 0; //최종 시간
                                                                    while(true) {
                                                                        if (task.isSuccessful()) {
                                                                            for (QueryDocumentSnapshot ds : task.getResult()) {
                                                                                Integer interval_temp = Integer.parseInt(ds.get("interval").toString());
                                                                                interval = interval + interval_temp;
                                                                                Log.d("intev", "new interval : " + interval_temp);
                                                                            }
                                                                            Log.d("test", "Result interval : " + interval);
                                                                            Result.add(interval);
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                }
                                            });

                                }
                            });
                    for(Integer temp : Result){
                        Log.d("test", "result ::\n" );
                        Log.d("test",temp.toString()+'\n');
                    }
                }
            }
        });
    }
}