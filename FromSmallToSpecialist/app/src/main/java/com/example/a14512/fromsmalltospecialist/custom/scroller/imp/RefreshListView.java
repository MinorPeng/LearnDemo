package com.example.a14512.fromsmalltospecialist.custom.scroller.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.a14512.fromsmalltospecialist.custom.scroller.RefreshAdapterView;

/**
 * @author 14512 on 2018/4/3
 */

public class RefreshListView extends RefreshAdapterView<ListView> {

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setupContentView(Context context) {
        mContentView = new ListView(context);
        mContentView.setOnScrollListener(this);
    }

    @Override
    protected boolean isTop() {
        return mContentView.getFirstVisiblePosition() == 0
                && getScrollY() <= mHeaderView.getMeasuredHeight();
    }

    @Override
    protected boolean isBottom() {
        return mContentView != null && mContentView.getAdapter() != null
                && mContentView.getLastVisiblePosition() == mContentView.getAdapter().getCount() - 1;
    }
}
