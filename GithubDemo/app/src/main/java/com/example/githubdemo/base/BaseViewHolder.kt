package com.example.githubdemo.base

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

/**
 * @author 14512 on 2019/4/17
 */
class BaseViewHolder(private val mItemView: View) : RecyclerView.ViewHolder(mItemView) {
    private val mViews = SparseArray<View>()

    /**
     * 根据id获取当前布局中的id
     * @param viewId
     * @return
     */
    fun <T : View> getView(viewId: Int): T {
        var view = mViews[viewId]
        if (view == null) {
            view = mItemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }
}