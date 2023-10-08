package com.example.learning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.my_btn);
        findViewById(R.id.enable_btn).setOnClickListener(this);
        findViewById(R.id.disable_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.enable_btn){
            mButton.setEnabled(true);
        } else if (view.getId() == R.id.disable_btn) {
            mButton.setEnabled(false);
        }
    }
}
