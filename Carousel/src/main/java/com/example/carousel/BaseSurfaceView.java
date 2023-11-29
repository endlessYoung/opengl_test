package com.example.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Paint paint;
    int[] colors = {Color.rgb(255, 165, 0), Color.rgb(255, 25, 0)};

    private float[] positions = {0.0f, 1.0f};

    public BaseSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
        paint = new Paint();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 在 Surface 创建时调用，可以在这里进行一些初始化操作
        drawGradient();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    private void drawGradient() {
        Canvas canvas = getHolder().lockCanvas(); // 获取 Canvas
        if (canvas != null) {
            try {
                // 创建线性渐变
                Shader shader = new LinearGradient(0, 0, 0, getHeight(), colors, positions, Shader.TileMode.CLAMP);

                // 设置渐变为画笔的着色器
                paint.setShader(shader);
                paint.setAntiAlias(true);
                paint.setDither(true);

                // 绘制矩形，即整个 SurfaceView 区域
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
            } finally {
                // 解锁 Canvas，提交绘制内容
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

}
