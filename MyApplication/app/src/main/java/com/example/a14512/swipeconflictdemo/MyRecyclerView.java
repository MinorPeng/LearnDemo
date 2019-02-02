package com.example.a14512.swipeconflictdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 14512 on 2017/9/10.
 */

public class MyRecyclerView extends RecyclerView {
    private float lastX;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            getParent().getParent().requestDisallowInterceptTouchEvent(true);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            int x = (int) ev.getX();
            if (lastX > x) {
                // 如果是水平向左滑动，且不能滑动了，则返回给上一层view处理
                if (!canScrollHorizontally(1)) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            } else if (x > lastX) {
                // 如果是水平向右滑动，且不能滑动了，则返回给上一层view处理
                if (!canScrollHorizontally(-1)) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            }
        }
        lastX = ev.getX();
        return super.dispatchTouchEvent(ev);
    }
}
