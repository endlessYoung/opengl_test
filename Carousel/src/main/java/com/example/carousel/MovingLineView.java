package com.example.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class MovingLineView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private float startX, startY, stopX, stopY; // 当前线条的位置
    private int lineColor;

    private boolean isRunning;
    private Thread thread;

    public MovingLineView(Context context) {
        super(context);
        init();
    }

    public MovingLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovingLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        lineColor = Color.RED; // 初始颜色为红色
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        startX = 5;
        startY = 5;
        stopX = 5;
        stopY = 0.8f * getHeight();

        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 处理Surface尺寸变化
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            draw();
            update();
            sleep();
        }
    }

    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            try {
                canvas.drawColor(Color.WHITE);
                paint.setColor(lineColor);
                canvas.drawLine(startX, startY, stopX, stopY, paint);
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void update() {
        // 在这里更新线条的位置和颜色
        Random random = new Random();

        startY += 20;
        stopY += 20;

        paint.setDither(true);

        // 游动在设备四个边框的边缘
//        if (x <= 0 || x >= screenWidth || y <= 0 || y >= screenHeight) {
//            x = screenWidth / 2;
//            y = screenHeight / 2;
//        } else {
//            // 在设备边框的边缘游动
//            int direction = random.nextInt(4);
//            Log.d("TAG", "update: " + direction);
//            switch (direction) {
//                case 0: // 上边框
//                    x = random.nextInt(screenWidth);
//                    y = 0;
//                    break;
//                case 1: // 右边框
//                    x = screenWidth;
//                    y = random.nextInt(screenHeight);
//                    break;
//                case 2: // 下边框
//                    x = random.nextInt(screenWidth);
//                    y = screenHeight;
//                    break;
//                case 3: // 左边框
//                    x = 0;
//                    y = random.nextInt(screenHeight);
//                    break;
//            }
//        }

        lineColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private void sleep() {
        try {
            Thread.sleep(500); // 控制线程刷新的速度
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
