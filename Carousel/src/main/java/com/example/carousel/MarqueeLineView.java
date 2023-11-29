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

public class MarqueeLineView extends View {
    private Paint paint;
    private RectF rectF;
    private Path[] paths;
    private PathMeasure[] pathMeasures;
    private ValueAnimator animator;
    private float[] offsets;

    public MarqueeLineView(Context context) {
        super(context);
        init();
    }

    public MarqueeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        rectF = new RectF(50, 50, 550, 250); // 定义圆角矩形的矩形区域

        int pathCount = 5; // 路径数量
        paths = new Path[pathCount];
        pathMeasures = new PathMeasure[pathCount];
        offsets = new float[pathCount];

        for (int i = 0; i < pathCount; i++) {
            paths[i] = new Path();
            paths[i].addRoundRect(rectF, 110, 110, Path.Direction.CW);

            pathMeasures[i] = new PathMeasure(paths[i], false);
            offsets[i] = i * pathMeasures[i].getLength() / (pathCount - 1);
        }

        // 创建动画，让线段沿着路径移动
        animator = ValueAnimator.ofFloat(0, pathMeasures[0].getLength());
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                for (int i = 0; i < pathCount; i++) {
                    offsets[i] = (value + i * pathMeasures[i].getLength() / (pathCount - 1)) % pathMeasures[i].getLength();
                }
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < paths.length; i++) {
            canvas.save();
            canvas.translate(offsets[i], 0);
            canvas.drawPath(paths[i], paint);
            canvas.restore();
        }
    }
}
