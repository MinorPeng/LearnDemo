package com.example.a14512.customviewdemo.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 14512 on 2018/7/21
 */
public class BezierView2 extends View {
    private Paint mPaint;
    private int mCenterX, mCenterY;

    private PointF mStart, mEnd, mControl1, mControl2;
    private boolean mode = true;

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        mStart = new PointF(0, 0);
        mEnd = new PointF(0, 0);
        mControl1 = new PointF(0, 0);
        mControl2 = new PointF(0, 0);
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public BezierView2(Context context) {
        this(context, null);
    }

    public BezierView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;

        mStart.x = mCenterX - 200;
        mStart.y = mCenterY;
        mEnd.x = mCenterX + 200;
        mEnd.y = mCenterY;
        mControl1.x = mCenterX;
        mControl1.y = mCenterY - 100;
        mControl2.x = mCenterX;
        mControl2.y = mCenterY - 100;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoints(new float[] {mStart.x, mStart.y,
                mEnd.x, mEnd.y,
                mControl1.x, mControl1.y,
                mControl2.x, mControl2.y}, mPaint);

        mPaint.setStrokeWidth(4);
        canvas.drawLines(new float[] {mStart.x, mStart.y, mControl1.x, mControl1.y,
                mControl2.x, mControl2.y, mEnd.x, mEnd.y}, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        path.moveTo(mStart.x, mStart.y);
        path.cubicTo(mControl1.x, mControl1.y, mControl2.x, mControl2.y, mEnd.x, mEnd.y);
        canvas.drawPath(path, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mode) {
            mControl1.x = event.getX();
            mControl1.y = event.getY();
            invalidate();
        } else {
            mControl2.x = event.getX();
            mControl2.y = event.getY();
            invalidate();
        }
        return true;
    }
}
