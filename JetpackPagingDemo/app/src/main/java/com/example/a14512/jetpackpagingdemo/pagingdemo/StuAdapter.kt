package com.example.a14512.jetpackpagingdemo.pagingdemo

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.a14512.jetpackpagingdemo.R

/**
 * @author 14512 on 2018/8/23
 */
class StuAdapter : PagedListAdapter<StuBean, StuAdapter.StuViewHolder>(diffCallback) {
    private lateinit var mContext: Context
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<StuBean>() {
            override fun areItemsTheSame(oldItem: StuBean?, newItem: StuBean?): Boolean =
                    oldItem?.id == newItem?.id

            override fun areContentsTheSame(oldItem: StuBean?, newItem: StuBean?): Boolean =
                    oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StuViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_main, parent, false)
        return StuViewHolder(view)
    }

    override fun onBindViewHolder(holder: StuViewHolder, position: Int) {
        holder.tvTitle.text = getItem(position)?.name
    }

    class StuViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView?.findViewById(R.id.tv_item_recycler)!!
    }
}