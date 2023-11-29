package com.example.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletsView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder holder;
    private Paint paint;
    private List<Bullet> bullets;
    private boolean isRunning;
    private Random random;

    public BulletsView(Context context) {
        super(context);
        init();
    }

    public BulletsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BulletsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);

        paint = new Paint();
        bullets = new CopyOnWriteArrayList<>();
        random = new Random();
        isRunning = true;
    }

    public void addBullet(float startX, float startY, int color, float speed) {
        Bullet bullet = new Bullet(startX, startY, color, speed);
        bullets.add(bullet);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            updateBullets();
            drawCanvas();
        }
    }

    private void updateBullets() {
        for (Bullet bullet : bullets) {
            bullet.update();
        }
    }

    private void drawCanvas() {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            drawBackground(canvas);
            drawBullets(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.BLACK); // 设置背景颜色
    }

    private void drawBullets(Canvas canvas) {
        for (Bullet bullet : bullets) {
            paint.setColor(bullet.color);
            canvas.drawLine(bullet.x, bullet.y, bullet.radius,bullet.radius, paint);
        }
    }

    private class Bullet {
        float x, y;
        int color;
        float speed;
        float radius;

        Bullet(float startX, float startY, int color, float speed) {
            this.x = startX;
            this.y = startY;
            this.color = color;
            this.speed = speed;
            this.radius = 10; // 子弹半径，你可以根据需要调整
        }

        void update() {
            x += speed;

            // 判断是否超出屏幕，如果是，则从列表中移除
            if (x > getWidth()) {
                bullets.remove(this);
            }
        }
    }
}
