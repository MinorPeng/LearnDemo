package com.example.githubdemo.moudels.main.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.githubdemo.R
import com.example.githubdemo.base.BaseRecyclerAdapter
import com.example.githubdemo.base.BaseViewHolder
import com.example.githubdemo.common.utils.GlideUtil
import com.example.githubdemo.moudels.User

/**
 * @author 14512 on 2019/4/17
 */
class UserAdapter : BaseRecyclerAdapter<User>(R.layout.item_recycler_footer) {

    private var mFooterView: TextView? = null

    private var mLastItemState = 99
    //上拉加载更多
    private val PULLUP_LOAD_MORE = 100
    //正在加载中
    private val LOADING_MORE = 101
    //没有更多了
    private val NO_DATA_MORE = 102

    override fun getItemLayoutId(): Int = R.layout.item_recycler_user

    override fun bindHolder(holder: BaseViewHolder, position: Int) {
        val user = getData(position)
        user?.let {
            val tvName = holder.getView<TextView>(R.id.tvItemUserName)
            val tvStars = holder.getView<TextView>(R.id.tvItemStars)
            val img = holder.getView<ImageView>(R.id.imgPortrait)
            tvName.text = user.name.toString()
            tvStars.text = user.stars.toString()
            user.owner?.let {
                GlideUtil.default(img.context, it.portrait, img)
            }

            holder.itemView.setOnClickListener {
                mClickListener?.onClick(it, position)
            }

            holder.itemView.setOnLongClickListener {
                mClickListener?.onLongClick(it, position)
                return@setOnLongClickListener true
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bindFooter(holder: BaseViewHolder, position: Int) {
        mFooterView = holder.itemView as TextView
        mFooterView?.visibility = View.VISIBLE
        when (mLastItemState) {
            PULLUP_LOAD_MORE -> {
                mFooterView?.text = "load more..."
            }
            LOADING_MORE -> {
                mFooterView?.text = "loading..."
            }
            NO_DATA_MORE -> {
                mFooterView?.text = "----no more data----"
            }
        }
    }

    fun noMoreFooter() {
        mLastItemState = NO_DATA_MORE
    }

    fun loadMoreFooter() {
        mLastItemState = LOADING_MORE
    }

}