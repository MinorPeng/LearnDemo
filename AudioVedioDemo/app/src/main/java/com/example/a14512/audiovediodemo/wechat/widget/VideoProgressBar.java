package com.example.a14512.audiovediodemo.wechat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.a14512.audiovediodemo.R;

/**
 * @author 14512 on 2018/9/6
 */
public class VideoProgressBar extends View {

    private boolean mIsCancel = false;
    private Paint mRecordPaint, mBgPaint;
    private RectF mRectF;
    private int mProgress;
    private OnProgressEndListener mOnProgressEndListener;
    private int mWith, mHeight;

    public VideoProgressBar(Context context) {
        this(context, null);
    }

    public VideoProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRecordPaint = new Paint();
        mBgPaint = new Paint();
        mRectF = new RectF();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWith = getWidth();
        mHeight = getHeight();

        if (mWith != mHeight) {
            int min = Math.min(mWith, mHeight);
            mWith = min;
            mHeight = min;
        }

        int circleLineStrokeWith = 10;
        mRecordPaint.setAntiAlias(true);
        mRecordPaint.setStrokeWidth(circleLineStrokeWith);
        mRecordPaint.setStyle(Paint.Style.STROKE);

        mRectF.left = circleLineStrokeWith / 2 + .8f;
        mRectF.top = circleLineStrokeWith / 2 + .8f;
        mRectF.right = circleLineStrokeWith / 2 - 1.5f;
        mRectF.bottom = circleLineStrokeWith /2 - 1.5f;

        mBgPaint.setAntiAlias(true);
        mBgPaint.setStrokeWidth(circleLineStrokeWith);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(getResources().getColor(R.color.btn_bg));
        canvas.drawCircle(mWith / 2, mHeight / 2, mWith / 2 - 0.5f, mBgPaint);

        if (mIsCancel) {
            mRecordPaint.setColor(Color.TRANSPARENT);
            canvas.drawArc(mRectF, -90, 360, false, mRecordPaint);
            mIsCancel = false;
            return;
        }

        int maxProgress = 100;
        if (mProgress > 0 && mProgress < maxProgress) {
            mRecordPaint.setColor(Color.rgb(0x00, 0xc0, 0x7f));
            canvas.drawArc(mRectF, -90, ((float) mProgress / maxProgress) * 360, false, mRecordPaint);
        } else if (mProgress == 0) {
            mRecordPaint.setColor(Color.TRANSPARENT);
            canvas.drawArc(mRectF, -90, 360, false, mRecordPaint);
        } else if (mProgress == maxProgress) {
            if (mOnProgressEndListener != null) {
                mOnProgressEndListener.onProgressEndListener();
            }
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void setCancel(boolean isCancel) {
        mIsCancel = isCancel;
        invalidate();
    }

    public void setOnProgressEndListener(OnProgressEndListener progressEndListener) {
        mOnProgressEndListener = progressEndListener;
    }

    public interface OnProgressEndListener {
        /**
         * ???
         */
        void onProgressEndListener();
    }
}
