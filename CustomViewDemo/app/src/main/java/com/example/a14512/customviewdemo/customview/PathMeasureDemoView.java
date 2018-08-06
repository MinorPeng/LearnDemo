package com.example.a14512.customviewdemo.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.a14512.customviewdemo.R;

/**
 * @author 14512 on 2018/7/22
 */
public class PathMeasureDemoView extends View {
    private Paint mPaint;
    private float mCurrentValue = 0;
    private float[] mPosition;
    private float[] mTan;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private int mWith;
    private int mHeight;

    public void init(Context context) {
        mPaint = new Paint();
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPosition = new float[2];
        mTan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        //缩放
        options.inSampleSize = 8;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.derection, options);
        mMatrix = new Matrix();
    }

    public PathMeasureDemoView(Context context) {
        this(context, null);
    }

    public PathMeasureDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathMeasureDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWith = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWith / 2, mHeight / 2);
        @SuppressLint("DrawAllocation") Path path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);

        @SuppressLint("DrawAllocation") PathMeasure measure = new PathMeasure(path, false);
        mCurrentValue += 0.005;
        if (mCurrentValue >= 1) {
            mCurrentValue = 0;
        }

//        measure.getPosTan(measure.getLength() * mCurrentValue, mPosition, mTan);
//        mMatrix.reset();
//        float degrees = (float) (Math.atan2(mTan[1], mTan[0])  * 180.0 / Math.PI);
//        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
//        mMatrix.postTranslate(mPosition[0] - mBitmap.getWidth() / 2,
//                mPosition[1] - mBitmap.getHeight() / 2);

        // 获取当前位置的坐标以及趋势的矩阵、与上面五行等价
        measure.getMatrix(measure.getLength() * mCurrentValue, mMatrix,
                PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)
        // 矩阵对旋转角度默认为图片的左上角，我们此处需要使用 preTranslate 调整为图片中心。
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);



        canvas.drawPath(path, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        invalidate();
    }
}
