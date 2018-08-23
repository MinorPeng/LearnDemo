package com.example.a14512.scollertest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author 14512 on 2018/8/19
 */
public class ScrollerLayout extends ViewGroup {
    private Scroller mScroller;
    private int mTouchSlop;
    private float mXDown;
    private float mXMove;
    private float mXLastMove;

    private int mLeftBorder;
    private int mRightBorder;

    public ScrollerLayout(Context context) {
        this(context, null);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        //获取TouchSlop值
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //为ScrollerLayout中得每一个子控件测量大小
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                child.layout(i * child.getMeasuredWidth(), 0, (i + 1) * child.getMeasuredWidth(),
                        child.getMeasuredHeight());
            }
            mLeftBorder = getChildAt(0).getLeft();
            mRightBorder = getChildAt(childCount - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                //当手指拖动值大于TouchSlop，视为滚动，拦截
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);
                if (getScrollX() + scrolledX < mLeftBorder) {
                    scrollTo(mLeftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > mRightBorder) {
                    scrollTo(mRightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面，滑动
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                //调用startScroll来初始化滚动数据并刷新
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        //完成平滑滚动
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
