package com.phs1024.studydemo.theory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author PHS1024
 * @date 2019/10/17 10:27:55
 */
public class CustomView extends View {

    private Paint mPaint;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(200, 200);
        //draw rect
        RectF rect1 = new RectF(10, 10, 100, 110);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect1, mPaint);

        RectF rectF = new RectF(150, 10, 250, 110);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectF, mPaint);

        //circle
        canvas.drawCircle(150, 150, 36, mPaint);

        //triangle
        Path path = new Path();
        path.moveTo(150, 200);
        path.lineTo(250, 300);
        path.lineTo(50, 300);
        path.lineTo(150, 200);
        canvas.drawPath(path, mPaint);

        //text
        mPaint.setTextSize(36);
        canvas.drawText("几何图形示例", 50, 400, mPaint);
    }
}
