package com.example.learning;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.KeyEventDispatcher;

public class JumpFistActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jump_first_activity);
        findViewById(R.id.go_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(this,JumpSecondActivity.class);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:" + 123456);
        intent.setData(uri);
//        ComponentName componentName = new ComponentName(this,JumpSecondActivity.class);
//        intent.setComponent(componentName);
//        intent.setClass(this,JumpSecondActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
