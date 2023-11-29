package com.example.carousel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);

//        DancingLinesView dancingLinesView = findViewById(R.id.gradientSurfaceView);
//        dancingLinesView.addDancingLines(5,0,5,2,10f,Color.RED);
//        dancingLinesView.addDancingLines(5,0,5,10,11f,Color.BLUE);
//        dancingLinesView.addDancingLines(5,0,5,50,12f,Color.RED);
//        dancingLinesView.addDancingLines(5,0,5,80,13f,Color.BLUE);
//        dancingLinesView.addDancingLines(5,0,5,100,14f,Color.RED);
//        dancingLinesView.addDancingLines(5,0,5,100,15f,Color.BLUE);
//        dancingLinesView.addDancingLines(5,0,5,200,16f,Color.RED);
//        dancingLinesView.addDancingLines(5,0,5,300,17f,Color.BLUE);
//        dancingLinesView.addDancingLines(5,0,5,400,18f,Color.RED);
//        dancingLinesView.addDancingLines(5,0,5,500,19f,Color.BLUE);

//        dancingLinesView.addDancingLines(5,DancingLinesView.screenHeight,10,DancingLinesView.screenHeight,10f,Color.RED);
//        dancingLinesView.addDancingLines(5,DancingLinesView.screenHeight,15,DancingLinesView.screenHeight,10f,Color.BLUE);
//        dancingLinesView.addDancingLines(5,DancingLinesView.screenHeight,20,DancingLinesView.screenHeight,10f,Color.RED);
//        dancingLinesView.addDancingLines(5,DancingLinesView.screenHeight,25,DancingLinesView.screenHeight,10f,Color.BLUE);
//        dancingLinesView.addDancingLines(5,DancingLinesView.screenHeight,30,DancingLinesView.screenHeight,10f,Color.RED);
//        dancingLinesView.addDancingLines(5,DancingLinesView.screenHeight,35,DancingLinesView.screenHeight,10f,Color.BLUE);

//        BulletsView danmakuView = findViewById(R.id.gradientSurfaceView);
//        danmakuView.addBullet(0, 100, Color.RED, 5.0f);  // 添加红色子弹
//        danmakuView.addBullet(0, 200, Color.BLUE, 8.0f); // 添加蓝色子弹
//        danmakuView.addBullet(0, 300, Color.RED, 3.0f); // 添加蓝色子弹
//        danmakuView.addBullet(0, 400, Color.BLUE, 6.0f); // 添加蓝色子弹
//        danmakuView.addBullet(0, 500, Color.RED, 7.0f); // 添加蓝色子弹
//        danmakuView.addBullet(0, 600, Color.BLUE, 9.0f); // 添加蓝色子弹
    }
}