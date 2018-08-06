package com.example.a14512.jetpackpagingdemo

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.a14512.jetpackpagingdemo.bean.KituItem
import kotlinx.android.synthetic.main.item_kitu_recycler.view.*

/**
 * @author 14512 on 2018/7/7
 */
class KituPagedListAdapter : PagedListAdapter<KituItem, KituViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KituViewHolder {
        return KituViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_kitu_recycler, parent, false))
    }

    override fun onBindViewHolder(holder: KituViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<KituItem>() {
            override fun areItemsTheSame(oldItem: KituItem, newItem: KituItem): Boolean
                    = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: KituItem, newItem: KituItem): Boolean
                    = oldItem == newItem

        }
    }
}


class KituViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var item : KituItem? = null

    fun bindTo(item : KituItem?) {
        this.item = item
        itemView.itemTypeView.text = item?.type?.capitalize()
                ?: "Ouhh..."
        itemView.itemSubtypeView.text = item?.attributes?.subtype?.capitalize()
                ?: "Ouhhhhh..."
        itemView.itemNameView.text = item?.attributes?.titles?.en_jp?.capitalize()
                ?: "Ouhhhhhhhh..."
        itemView.itemSynopsisView.text = item?.attributes?.synopsis?.capitalize()
                ?: "Ouhhhhhhhhhhh...\nYou know what?\nThe quick brown fox jumps over the lazy dog!"
        val imageUrl = item?.attributes?.posterImage?.small
        if (null != imageUrl) {
            itemView.itemCoverView.visibility = View.VISIBLE
            Glide.with(itemView.context)
                    .load(imageUrl)
                    .apply(RequestOptions().placeholder(R.drawable.empty_placeholder))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemView.itemCoverView)
        } else {
            Glide.with(itemView.context).clear(itemView.itemCoverView)
            itemView.itemCoverView.setImageResource(R.drawable.empty_placeholder)
        }
    }

}
