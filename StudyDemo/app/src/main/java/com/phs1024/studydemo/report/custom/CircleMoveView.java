package com.phs1024.studydemo.report.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author PHS1024
 * @date 2019/10/14 10:29:20
 */
public class CircleMoveView extends View {

    private PointF mPosition;
    private PointF mPCircle;
    private Paint mPaint;
    private float mRadius = 30f;
    private int mColorRes = android.R.color.holo_blue_dark;
    private MoveListener mMoveListener = null;
    private float mLastX;
    private float mLastY;

    public CircleMoveView(Context context) {
        this(context, null);
    }

    public CircleMoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPosition = new PointF();
        mPCircle = new PointF();
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(context, mColorRes));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPCircle.x = getMeasuredWidth() >> 1;
        mPCircle.y = getMeasuredHeight() >> 1;
        mRadius = mPCircle.x > mPCircle.y ? mPCircle.y : mPCircle.x;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mPCircle.x, mPCircle.y, mRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                //通过ViewParent去重新绘制子view
                offsetLeftAndRight((int) (x - mLastX));
                offsetTopAndBottom((int) (y - mLastY));
                mPosition.x = event.getRawX();
                mPosition.y = event.getRawY();
                if (mMoveListener != null) {
                    mMoveListener.position(mPosition);
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }



    public void setColor(int colorRes) {
        mColorRes = colorRes;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public void setMoveListener(MoveListener moveListener) {
        mMoveListener = moveListener;
    }

    public interface MoveListener {
        /**
         * posision listener
         * @param pointF
         */
        void position(PointF pointF);
    }
}
