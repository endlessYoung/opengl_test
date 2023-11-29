package com.example.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.concurrent.CopyOnWriteArrayList;

public class DancingLinesView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder surfaceHolder;
    private Path path;
    private Paint paint;
    private Thread thread;
    private boolean isRunning;
    public static int screenWidth;
    public static int screenHeight;
    private CopyOnWriteArrayList<DancingLine> dancingLinesList = new CopyOnWriteArrayList<>();


    public DancingLinesView(Context context) {
        super(context);
        init();
    }

    public DancingLinesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DancingLinesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        screenWidth = getWidth();
        screenHeight = getHeight();

        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * @param surfaceHolder
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    /**
     * @param surfaceHolder
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
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
            updateDancingLines();
        }
    }

    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            try {
                canvas.drawColor(Color.WHITE);
                paint.setColor(Color.RED);
                drawDancingLine(canvas);
            } catch (Exception e) {
                Log.d("TAG", "draw: " + e.getMessage());
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void updateDancingLines() {
        for (DancingLine dancingLine : dancingLinesList) {
            dancingLine.update();
        }
    }

    private void drawDancingLine(Canvas canvas) {
        float left = 50;
        float top = 50;
        float right = screenWidth - 50;
        float bottom = screenHeight - 50;
        float radius = 30;

        path = new Path();
        path.addRoundRect(new RectF(left, top, right, bottom), radius, radius, Path.Direction.CW);
        for (DancingLine dancingLine : dancingLinesList) {
            paint.setColor(dancingLine.color);
//            canvas.drawPath(path, paint);
            canvas.drawLine(dancingLine.x1, dancingLine.y1, dancingLine.x2, dancingLine.y2, paint);
        }
    }

    public void addDancingLines(int x1, int y1, int x2, int y2, float speed, int color) {
        DancingLine dancingLine = new DancingLine(x1, y1, x2, y2, speed, color);
        dancingLinesList.add(dancingLine);
    }

    private class DancingLine {
        float x1, y1, x2, y2;
        float speed;
        int color;

        DancingLine(float startX, float startY, float stopX, float stopY, float speed, int color) {
            this.x1 = startX;
            this.y1 = startY;
            this.x2 = stopX;
            this.y2 = stopY;
            this.speed = speed;
            this.color = color;
        }

        void update() {
            y1 += speed;
            y2 += speed;

//            PathMeasure pathMeasure = new PathMeasure(path, false);
//            float pathLength = pathMeasure.getLength();
//            float[] position = new float[2];
//
//            // 计算当前位置
//            float distance = speed;
//            pathMeasure.getPosTan(distance, position, null);
//
//            this.x1 = position[0];
//            this.y1 = position[1];
//
//            // 如果线的一端超过路径长度，则从路径起点重新开始
//            if (distance >= pathLength) {
//                distance = 0;
//            }
//
//            // 更新路径上的第二个点
//            pathMeasure.getPosTan(distance, position, null);
//            this.x2 = position[0];
//            this.y2 = position[1];

//            x1 += speed;
//            x2 += speed;

            if (this.y1 > screenHeight) {
                this.y1 = 0;
            }

//            if (this.x1 > screenWidth){
//                this.x1 = 0;
//            }

            invalidate();
        }
    }
}
