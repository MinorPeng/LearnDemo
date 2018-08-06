package com.example.a14512.customviewdemo.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 14512 on 2018/7/20
 */
public class PathViewDemo extends View {
    private Paint mPaint;
    private float mWith;
    private float mHeight;

    private void initPaint() {
        mPaint = new Paint();
        //防止边缘的锯齿
        mPaint.setAntiAlias(true);
        //对位图进行滤波处理
        mPaint.setFilterBitmap(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(10);
    }

    public PathViewDemo(Context context) {
        this(context, null);
    }

    public PathViewDemo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathViewDemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWith / 2, mHeight / 2);
        @SuppressLint("DrawAllocation") Path path = new Path();
        path.lineTo(200, 200);
        path.lineTo(200, 0);
//        canvas.drawPath(path, mPaint);
        @SuppressLint("DrawAllocation") Path path1 = new Path();
        @SuppressLint("DrawAllocation") Path path2 = new Path();
        @SuppressLint("DrawAllocation") Path path3 = new Path();
        @SuppressLint("DrawAllocation") Path path4 = new Path();

        path1.addCircle(0, 0, 200, Path.Direction.CW);
        path2.addRect(0, -200, 200, 200, Path.Direction.CW);
        path3.addCircle(0, -100, 100, Path.Direction.CW);
        path4.addCircle(0, 100, 100, Path.Direction.CCW);

        //DIFFERENCE path1-path2剩下的
        //REVERSE_DIFFERENCE Path2中减去Path1后剩下的部分
        //INTERSECT Path1与Path2相交的部分
        //UNION 包含全部Path1和Path2
        //XOR 包含Path1与Path2但不包括两者相交的部分
        path1.op(path2, Path.Op.DIFFERENCE);
        path1.op(path3, Path.Op.UNION);
        path1.op(path4, Path.Op.DIFFERENCE);

        canvas.drawPath(path1, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWith = w;
        mHeight = h;
    }
}
