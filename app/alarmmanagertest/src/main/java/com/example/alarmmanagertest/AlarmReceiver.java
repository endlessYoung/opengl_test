package com.example.alarmmanagertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 在这里执行闹钟触发时的操作，例如显示通知或启动服务等
        Log.d("AlarmReceiver", "Alarm Triggered!");

        // 示例：显示一个Toast消息
        Toast.makeText(context, "闹钟响了！", Toast.LENGTH_LONG).show();
    }
}

