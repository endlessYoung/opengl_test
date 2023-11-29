package com.example.alarmmanagertest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setAlarmButton = findViewById(R.id.setAlarmButton);
        Button cancelAlarmButton = findViewById(R.id.cancelAlarmButton);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置闹钟，在1000毫秒后触发
                AlarmManagerHelper.setAlarm(MainActivity.this, 1000);
            }
        });

        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消闹钟
                AlarmManagerHelper.cancelAlarm(MainActivity.this);
            }
        });
    }
}
