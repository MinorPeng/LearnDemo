package com.example.a14512.fromsmalltospecialist.custom.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListAdapter;

/**
 * @author 14512 on 2018/4/3
 */

public  abstract class RefreshAdapterView<T extends AbsListView> extends RefreshLayoutBase<T> {

    public RefreshAdapterView(Context context) {
        this(context, null);
    }

    public RefreshAdapterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshAdapterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(ListAdapter adapter) {
        mContentView.setAdapter(adapter);
    }

    public ListAdapter getAdapter() {
        return mContentView.getAdapter();
    }
}
