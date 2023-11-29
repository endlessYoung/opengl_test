package com.example.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class XfermodeExampleView extends View {

    private Paint paint;
    private PorterDuffXfermode xfermode;

    public XfermodeExampleView(Context context) {
        super(context);
        init();
    }

    public XfermodeExampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // 绘制一个矩形作为目标图像
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2 + 100, 200, Math.min(width, height) / 2, paint);

        // 设置 Xfermode
        paint.setXfermode(xfermode);

        // 绘制一个圆形作为源图像
        paint.setColor(Color.RED);
        canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, paint);
    }
}


