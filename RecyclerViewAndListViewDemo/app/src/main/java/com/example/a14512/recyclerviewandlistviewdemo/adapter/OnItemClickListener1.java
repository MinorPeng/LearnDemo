package com.example.a14512.recyclerviewandlistviewdemo.adapter;

import android.view.View;

/**
 * @author 14512 on 2018/4/29
 */
public interface OnItemClickListener1 {
    /**
     * 回调接口
     * @param view
     * @param position
     * @param str
     */
    void onItemClick(View view, int position, String str);
}
