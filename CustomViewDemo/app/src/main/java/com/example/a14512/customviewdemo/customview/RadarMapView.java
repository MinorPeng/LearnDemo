package com.example.a14512.customviewdemo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author 14512 on 2018/7/20
 */
public class RadarMapView extends View {

    /**
     * 数据个数
     */
    private int mCount = 6;
    private float mAngle = (float) (Math.PI * 2 / mCount);
    /**
     * 网格最大半径
     */
    private float mRadius;
    private float mCenterX;
    private float mCenterY;
    private Paint mMainPaint;
    private Paint mTextPaint;
    private Paint mValuePaint;

    private String[] mTitles = {"a","b","ccc","dd","ee","f"};
    private double[] mDatas = {100,60,60,60,100,50,10,20};
    private float mMaxValue = 100;

    public void setTitles(String[] titles) {
        mTitles = titles;
    }

    public void setDatas(double[] datas) {
        mDatas = datas;
    }

    public void setMaxValue(float maxValue) {
        mMaxValue = maxValue;
    }

    public void setNetColor(int netColor) {
        mMainPaint.setColor(netColor);
    }

    public void setTitleColor(int titleColor) {
        mTextPaint.setColor(titleColor);
    }

    public void setValueColor(int valueColor) {
        mValuePaint.setColor(valueColor);
    }

    private void initPaint() {
        mMainPaint = new Paint();
        mMainPaint.setStyle(Paint.Style.STROKE);
        mTextPaint = new Paint();
        mTextPaint.setTextSize(50);
        mValuePaint = new Paint();
    }

    public RadarMapView(Context context) {
        this(context, null);
    }

    public RadarMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRadius = Math.min(h, w)/ 2 * 0.9f;
        mCenterX = w / 2;
        mCenterY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }

    /**
     * 绘制正多边形
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //r是蜘蛛丝之间的间距
        float r = mRadius / (mCount - 1);
        for (int i = 1; i < mCount; i++) {
            float curR = r * i;
            //清楚路径中的内容，保留FillType
            path.reset();
            for (int j = 0; j < mCount; j++) {
                if (j == 0) {
                    path.moveTo(mCenterX + curR, mCenterY);
                } else {
                    float x = (float) (mCenterX + curR * Math.cos(mAngle * j));
                    float y = (float) (mCenterY + curR * Math.sin(mAngle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mMainPaint);
        }
    }

    /**
     * 绘制网格线
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mCount; i++) {
            path.reset();
            path.moveTo(mCenterX, mCenterY);
            float x = (float) (mCenterX + mRadius * Math.cos(mAngle * i));
            float y = (float) (mCenterY + mRadius * Math.sin(mAngle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mMainPaint);
        }
    }

    /**
     * 绘制文本
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < mCount; i++) {
            float x = (float) (mCenterX + (mRadius + fontHeight / 2) * Math.cos(mAngle * i));
            float y = (float) (mCenterY + (mRadius + fontHeight / 2) * Math.sin(mAngle * i));
            Log.e("RadarMapView", String.valueOf(mAngle * i));
            //象限判断有问题
            if(mAngle * i >= 0 && mAngle * i <= Math.PI / 2){
                //第4象限
                canvas.drawText(mTitles[i], x, y, mTextPaint);
            }else if(mAngle * i >= 3 * Math.PI / 2 && mAngle * i <= Math.PI * 2){
                //第1象限
                canvas.drawText(mTitles[i], x, y, mTextPaint);
            }else if(mAngle * i > Math.PI / 2 && mAngle * i <= Math.PI){
                //第3象限
                //文本长度
                float dis = mTextPaint.measureText(mTitles[i]);
                canvas.drawText(mTitles[i], x - dis, y ,mTextPaint);
            }else if(mAngle * i >= Math.PI && mAngle * i < 3 * Math.PI / 2){
                //第2象限
                //文本长度
                float dis = mTextPaint.measureText(mTitles[i]);
                canvas.drawText(mTitles[i], x - dis, y, mTextPaint);
            }
        }
    }

    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        mValuePaint.setAlpha(255);
        for (int i = 0; i < mCount; i++) {
            double percent = mDatas[i] / mMaxValue;
            float x = (float) (mCenterX + mRadius * Math.cos(mAngle * i) * percent);
            float y = (float) (mCenterY + mRadius * Math.sin(mAngle * i) * percent);
            if (i == 0) {
                path.moveTo(x, mCenterY);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 10, mValuePaint);
        }
        mValuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mValuePaint);
        mValuePaint.setAlpha(127);

        mValuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, mValuePaint);

    }
}
