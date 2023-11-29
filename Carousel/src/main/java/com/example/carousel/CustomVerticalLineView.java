package com.example.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomVerticalLineView extends View {

    private Paint paint;

    public CustomVerticalLineView(Context context) {
        super(context);
        init();
    }

    public CustomVerticalLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE); // 设置线条颜色
        paint.setStrokeWidth(5); // 设置线宽
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int startX = getWidth(); // 起始点 X 坐标，屏幕右侧留一些空白
        int startY = 0; // 起始点 Y 坐标，屏幕顶部
        int stopX = startX; // 终点 X 坐标，与起始点 X 相同，形成竖直线
        int stopY = getHeight(); // 终点 Y 坐标，屏幕底部

        // 在画布上绘制竖直线
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
}
