package com.example.githubdemo.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * @author 14512 on 2019/4/17
 */
abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder> {
    protected var mDatas: MutableList<T>? = null
    protected var mClickListener: BaseItemClickListener? = null
    private var mFooterId: Int? = null
    /**
     * footer view viewType
     */
    private val FOOTER_VIEW = -2

    constructor(datas: MutableList<T>?): this(datas, null)

    constructor(datas: MutableList<T>?, footerId: Int?) {
        checkNotNull()
        if (datas != null) {
            addAll(datas)
        }
        mFooterId = footerId
    }

    constructor(): this(null)

    constructor(footerId: Int): this(null, footerId)


    fun addAll(datas: MutableList<T>) {
        mDatas?.addAll(datas)
        notifyDataSetChanged()
    }

    fun add(data: T) {
        mDatas?.add(data)
        notifyDataSetChanged()
    }

    fun add(index: Int, data: T) {
        mDatas?.add(index, data)
        notifyDataSetChanged()
    }

    fun delete(data: T) {
        mDatas?.remove(data)
        notifyDataSetChanged()
    }

    fun delete(index: Int): T? {
        val data = mDatas?.removeAt(index)
        notifyDataSetChanged()
        return data
    }

    fun clear() {
        mDatas?.clear()
    }

    fun getData(index: Int): T? {
        if (index < 0 || index >= itemCount) {
            return null
        }
        return mDatas?.get(index)
    }

    fun getDatas(): MutableList<T>? {
        return mDatas
    }

    fun setClickListener(listener: BaseItemClickListener) {
        mClickListener = listener
    }

    private fun checkNotNull() {
        if (mDatas == null) {
            mDatas = ArrayList()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == FOOTER_VIEW && mFooterId != null) {
            val footerView = LayoutInflater.from(parent.context).inflate(mFooterId!!, parent, false)
            BaseViewHolder(footerView)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false)
            BaseViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == FOOTER_VIEW) {
            bindFooter(holder, position)
        } else {
            bindHolder(holder, position)
        }
    }

    override fun getItemCount(): Int = 0.takeIf { mDatas.isNullOrEmpty() } ?: mDatas!!.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (mFooterId != null && position == mDatas?.size) {
            FOOTER_VIEW
        } else {
            super.getItemViewType(position)
        }
    }

    /**
     * 获取需要绑定的item layout
     * @return layout id
     */
    abstract fun getItemLayoutId(): Int

    /**
     * 自己实现对item的操作
     */
    abstract fun bindHolder(holder: BaseViewHolder, position: Int)

    abstract fun bindFooter(holder: BaseViewHolder, position: Int)

}