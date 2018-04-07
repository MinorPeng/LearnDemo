package com.example.a14512.fromsmalltospecialist.second;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * @author 14512 on 2018/4/2
 */

public class ScrollLayout extends FrameLayout {
    private String TAG = ScrollLayout.class.getSimpleName();
    Scroller mScroller;

    public ScrollLayout(@NonNull Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    public ScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public ScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 在View重绘时调用
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            //滚动到此，view应该滚动到x、y坐标上
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //请求重绘该View，从而又会导致computeScroll呗调用，然后继续滚动
            //直到computeScrollOffset返回false
            this.postInvalidate();
        }
    }


    /**
     * 竖直方向的滚动
     * @param y
     */
    public void scrollTo(int y) {
        //参数1和参数2分别为滚动的起始点水平、垂直方向偏移量
        //参数3和参数4为在水平和垂直方向的滚动距离
        mScroller.startScroll(getScrollX(), getScrollY(), 0, y);
        this.invalidate();
    }
}
