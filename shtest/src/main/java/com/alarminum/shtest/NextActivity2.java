package com.alarminum.shtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alarminum.shtest.R;

public class NextActivity2 extends Activity{
    TextView textView;
    String JSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next2);
        Intent intent = getIntent();
        JSON = intent.getStringExtra("JSON");
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        Button button = findViewById(R.id.button);
        button.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText(JSON);
            }
        });
    }
}