package com.alarminum.yhtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> adspin1, adspin2, adspin3;
    String choice_do="";
    String choice_se="";
    String choice_des="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner spin1 = (Spinner)findViewById(R.id.spinner);
        final Spinner spin2 = (Spinner)findViewById(R.id.spinner2);
        final Spinner spin3 = (Spinner)findViewById(R.id.spinner3);
        Button btn_refresh = (Button)findViewById(R.id.btn_refresh);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.spinner_line, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);


        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if(adspin1.getItem(i).equals("1호선")){
                    choice_do = "1호선";
                    adspin2 = ArrayAdapter.createFromResource(MainActivity.this, R.array.spinner_start, android.R.layout.simple_spinner_dropdown_item);
                    adspin3 = ArrayAdapter.createFromResource(MainActivity.this, R.array.spinner_start, android.R.layout.simple_spinner_dropdown_item);

                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin3.setAdapter(adspin3);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_des = adspin3.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else if (adspin1.getItem(i).equals("2호선")){
                    choice_do = "2호선";
                    adspin2 = ArrayAdapter.createFromResource(MainActivity.this, R.array.spinner_destination, android.R.layout.simple_spinner_dropdown_item);
                    adspin3 = ArrayAdapter.createFromResource(MainActivity.this, R.array.spinner_destination, android.R.layout.simple_spinner_dropdown_item);

                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);
                    spin3.setAdapter(adspin3);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_se = adspin3.getItem(position).toString();
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
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, choice_do+"="+choice_se + " 에서 " + choice_des, Toast.LENGTH_SHORT).show();
            }
        });
    }


}