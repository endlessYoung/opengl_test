package com.example.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.nio.file.Path;

public class EdgeLinesView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Paint paint;
    private Path path;
    private SurfaceHolder holder;
    private Thread thread;
    private boolean isRunning;
    private int left, top, right, bottom;
    private RectF rectF;

    public EdgeLinesView(Context context) {
        super(context);
        init();

    }

    public EdgeLinesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EdgeLinesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            draw();
            update();
        }
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(15);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void draw() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        left = 0;
        top = -statusBarHeight;
        right = getWidth();
        bottom = getHeight();
        rectF = new RectF(left,top,right,bottom);

        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            try {
                canvas.drawColor(Color.WHITE);
                canvas.drawRoundRect(rectF,110,110,paint);
            } finally {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void update() {

    }
}
