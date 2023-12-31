package com.example.carousel;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class RoundRectPathAnimationView extends View {
    private Path path;
    private Paint bgPaint;
    private Paint slidePaint;
    private float startDistance, stopDistance;
    private float[] startPoint, stopPoint;
    private float valueAnimation;

    public RoundRectPathAnimationView(Context context) {
        super(context);
        init();
    }

    public RoundRectPathAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(20);
        bgPaint.setColor(Color.parseColor("#3b58b7"));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        slidePaint = new Paint();
        slidePaint.setStyle(Paint.Style.STROKE);
        slidePaint.setStrokeWidth(20);
        slidePaint.setColor(Color.parseColor("#02b8d4"));
        slidePaint.setStrokeCap(Paint.Cap.ROUND);

        setValueAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

//        Log.d("TAG", "width: " + width + ", height: " + height);
//        Log.d("TAG", "valueAnimation: " + valueAnimation);

        float left = 50;
        float top = 50;
        float right = width - 50;
        float bottom = height - 50;
        float radius = 30;

        path = new Path();
        path.addRoundRect(new RectF(left, top, right, bottom), radius, radius, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(path, false);
        float pathLength = measure.getLength();

        Path dst = new Path();
        startDistance = valueAnimation * pathLength;
        if (startDistance >= pathLength){
            startDistance = 0;
        }
        stopDistance = Math.min(1, valueAnimation + 0.1f) * pathLength;
        startPoint = new float[2];
        stopPoint = new float[2];
        measure.getPosTan(startDistance, startPoint, null);
        measure.getPosTan(stopDistance, stopPoint, null);
        measure.getSegment(startDistance, stopDistance, dst, true);

        canvas.drawPath(path, bgPaint);
        canvas.drawPath(dst, slidePaint);
    }

    private void setValueAnimation(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            valueAnimation = (float) valueAnimator1.getAnimatedValue();
            invalidate();
        });
        invalidate();
        valueAnimator.start();
    }
}

