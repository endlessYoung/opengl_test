package com.example.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomDiscontinuousRectView extends View {

    private Paint paint;

    public CustomDiscontinuousRectView(Context context) {
        super(context);
        init();
    }

    public CustomDiscontinuousRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED); // 设置线条颜色
        paint.setStrokeWidth(5); // 设置线宽
        paint.setStyle(Paint.Style.STROKE); // 设置绘制样式为描边
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float left = 100; // 左边距
        float top = 100; // 上边距
        float right = 300; // 右边距
        float bottom = 400; // 下边距
        float cornerRadius = 20; // 圆角半径

        // 绘制上边
        canvas.drawLine(left + cornerRadius, top, right - cornerRadius, top, paint);

        // 绘制右上角
        canvas.drawArc(right - 2 * cornerRadius, top, right, top + 2 * cornerRadius, -90, 90, false, paint);

        // 绘制右边
        canvas.drawLine(right, top + cornerRadius, right, bottom - cornerRadius, paint);

        // 绘制右下角
        canvas.drawArc(right - 2 * cornerRadius, bottom - 2 * cornerRadius, right, bottom, 0, 90, false, paint);

        // 绘制下边
        canvas.drawLine(right - cornerRadius, bottom, left + cornerRadius, bottom, paint);

        // 绘制左下角
        canvas.drawArc(left, bottom - 2 * cornerRadius, left + 2 * cornerRadius, bottom, 90, 90, false, paint);

        // 绘制左边
        canvas.drawLine(left, bottom - cornerRadius, left, top + cornerRadius, paint);

        // 绘制左上角
        canvas.drawArc(left, top, left + 2 * cornerRadius, top + 2 * cornerRadius, 180, 90, false, paint);
    }
}

