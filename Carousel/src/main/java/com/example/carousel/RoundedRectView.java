package com.example.carousel;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;

import android.view.animation.LinearInterpolator;

import android.graphics.Matrix;


public class RoundedRectView extends View {

    private Paint paint;
    private Path path;
    private SweepGradient sweepGradient;
    private int startAngle = 0;
    private int endAngle = 30;
    int[] gradientColors = {Color.RED, Color.BLACK, Color.RED};


    public RoundedRectView(Context context) {
        super(context);
        init();
    }

    public RoundedRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(20);
        paint.setAntiAlias(true);

        path = new Path();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        startAngle ++;
        endAngle ++;

        if (startAngle == 360){
            startAngle = 0;
            endAngle = 30;
        }

        float[] position = new float[3];
//        position[0] = startAngle / 360f;
//        position[1] = endAngle / 360f;

        position[0] = 0f;
        position[1] = startAngle / 360f;
        position[2] = endAngle / 360f;

        int width = getWidth();
        int height = getHeight();

        int cx = width / 2;
        int cy = height / 2;

        float left = 50;
        float top = 50;
        float right = width - 50;
        float bottom = height - 50;
        float radius = 20;

        path.addRoundRect(new RectF(left, top, right, bottom), radius, radius, Path.Direction.CW);

        sweepGradient = new SweepGradient(cx,cy, gradientColors, position);

        paint.setShader(sweepGradient);

        canvas.drawPath(path, paint);

        invalidate();
    }
}
