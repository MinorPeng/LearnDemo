package com.example.githubdemo.moudels.main.adapter

import android.widget.ImageView
import android.widget.TextView
import com.example.githubdemo.R
import com.example.githubdemo.base.BaseRecyclerAdapter
import com.example.githubdemo.base.BaseViewHolder
import com.example.githubdemo.common.utils.GlideUtil
import com.example.githubdemo.common.utils.LogUtil
import com.example.githubdemo.moudels.User

/**
 * @author 14512 on 2019/4/17
 */
class UserAdapter : BaseRecyclerAdapter<User>(R.layout.item_recycler_footer) {

    private var mFooterView: TextView? = null
    private var mLoadMore = true

    override fun getItemLayoutId(): Int = R.layout.item_recycler_user

    override fun bindHolder(holder: BaseViewHolder, position: Int) {
        val user = getData(position)
        LogUtil.e(this.javaClass.name, user.toString())
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

    override fun bindFooter(holder: BaseViewHolder, position: Int) {
        mFooterView = holder.itemView as TextView
    }

    fun noMoreFooter() {
        mFooterView?.text = "没有更多数据了!"
        mLoadMore = false
    }

    fun isLoadingMore(): Boolean = mLoadMore
}