package com.example.a14512.customviewdemo.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author 14512 on 2018/7/22
 */
public class SearchView extends View {
    private Paint mPaint;

    private int mWith;
    private int mHeight;

    private State mCurrentState = State.NONE;

    private Path mPathSearch;
    private Path mPathCircle;

    private PathMeasure mPathMeasure;

    /**
     * 默认的动效周期2s
     */
    private int mDefaultDuration = 2000;

    private ValueAnimator mStartingAnimator;
    private ValueAnimator mSearchingAnimator;
    private ValueAnimator mEndingAnimator;

    /**
     * 动画数值(用于控制动画状态,因为同一时间内只允许有一种状态出现,具体数值处理取决于当前状态)
     */
    private float mAnimatorValue = 0;

    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    private Handler mAnimatorHandler;

    private boolean mIsOver = false;
    private int mCount = 0;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAll();
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
        mPaint.setColor(Color.WHITE);
        canvas.translate(mWith / 2, mHeight / 2);
        canvas.drawColor(Color.parseColor("#0082D7"));

        switch (mCurrentState) {
            case NONE:
                canvas.drawPath(mPathSearch, mPaint);
                break;
            case STARTING:
                mPathMeasure.setPath(mPathSearch, false);
                @SuppressLint("DrawAllocation") Path dst = new Path();
                mPathMeasure.getSegment(mPathMeasure.getLength() * mAnimatorValue,
                        mPathMeasure.getLength(), dst, true);
                canvas.drawPath(dst, mPaint);
                break;
            case SEARCHING:
                mPathMeasure.setPath(mPathCircle, false);
                @SuppressLint("DrawAllocation") Path dst2 = new Path();
                float stop = mPathMeasure.getLength() * mAnimatorValue;
                float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * 200f));
                mPathMeasure.getSegment(start, stop, dst2, true);
                canvas.drawPath(dst2, mPaint);
                break;
            case ENDING:
                mPathMeasure.setPath(mPathSearch, false);
                @SuppressLint("DrawAllocation") Path dst3 = new Path();
                mPathMeasure.getSegment(mPathMeasure.getLength() * mAnimatorValue,
                        mPathMeasure.getLength(), dst3, true);
                canvas.drawPath(dst3, mPaint);
                break;
            default:
                break;
        }
    }

    private void initAll() {
        initPaint();
        initPath();
        initListener();
        initHandler();
        initAnimator();

        //开始动画监听，然后开始改变状态
        mStartingAnimator.start();
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    public void initPath() {
        mPathSearch = new Path();
        mPathCircle = new Path();

        mPathMeasure = new PathMeasure();

        RectF oval1 = new RectF(-50, -50, 50, 50);
        mPathSearch.addArc(oval1, 45, 359.9f);

        RectF oval2 = new RectF(-100, -100, 100, 100);
        mPathCircle.addArc(oval2, 45, -359.9f);

        float[] pos = new float[2];

        mPathMeasure.setPath(mPathCircle, false);
        mPathMeasure.getPosTan(0, pos, null);

        mPathSearch.lineTo(pos[0], pos[1]);

        Log.e("SearchView", "pos=" + pos[0] + ":" + pos[1]);
    }

    private void initListener() {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };

        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mAnimatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case NONE:
                        mCurrentState = State.STARTING;
                        mStartingAnimator.start();
                        break;
                    case STARTING:
                        //从开始动画转换为搜索动画
                        mIsOver = false;
                        mCurrentState = State.SEARCHING;
                        mStartingAnimator.removeAllUpdateListeners();
                        mSearchingAnimator.start();
                        break;
                    case SEARCHING:
                        if (!mIsOver) {
                            mSearchingAnimator.start();
                            Log.e("Update", "restart");
                            mCount++;
                            //控制搜索圈数
                            if (mCount > 2) {
                                mIsOver = true;
                            }
                        } else {
                            mCurrentState = State.ENDING;
                            mEndingAnimator.start();
                        }
                        break;
                    case ENDING:
                        mCurrentState = State.NONE;
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void initAnimator() {
        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(mDefaultDuration);
        mSearchingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(mDefaultDuration);
        mEndingAnimator = ValueAnimator.ofFloat(1, 0).setDuration(mDefaultDuration);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mSearchingAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartingAnimator.addListener(mAnimatorListener);
        mSearchingAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);
    }


    public enum State {
        /**
         * 最开始的状态
         */
        NONE,
        /**
         * 开始搜索的状态
         */
        STARTING,
        /**
         * 搜索的状态
         */
        SEARCHING,
        /**
         * 结束的状态
         */
        ENDING
    }
}
