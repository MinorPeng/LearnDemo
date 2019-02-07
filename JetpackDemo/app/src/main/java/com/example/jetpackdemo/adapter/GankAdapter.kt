package com.example.jetpackdemo.adapter

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.jetpackdemo.R
import com.example.jetpackdemo.bean.DateGank
import com.example.jetpackdemo.databinding.ItemStudyRecyclerBinding

/**
 * @author phs on 19-2-6
 */
class GankAdapter : ListAdapter<DateGank.DateGankData, GankAdapter.ViewHolder>(GankDiffCallback()) {
    private var mType = ""
    private var isGone = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_study_recycler, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = getItem(position)
        viewHolder.apply {
            if (!data.type.contentEquals(mType)) {
                mType = data.type
                isGone = false
            } else {
                isGone = true
            }
            bind(createListener(data.url), data, isGone)
        }
    }

    /**
     * 点击事件的跳转逻辑
     */
    private fun createListener(dataUrl: String): View.OnClickListener {
        return View.OnClickListener {
//            val direction = PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(plantId)
            val bundle = Bundle()
            bundle.putString("url", dataUrl)
            it.findNavController().navigate(R.id.action_gank_fragment_to_detail_fragment, bundle)
        }
    }

    class ViewHolder(private val binding: ItemStudyRecyclerBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: DateGank.DateGankData, isGone: Boolean) {
            binding.apply {
                this.isGone = isGone
                itemClickListener = listener
                data = item
                executePendingBindings()  //更新评估view
            }
        }
    }
}

private class GankDiffCallback : DiffUtil.ItemCallback<DateGank.DateGankData>() {
    override fun areItemsTheSame(p0: DateGank.DateGankData, p1: DateGank.DateGankData): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: DateGank.DateGankData, p1: DateGank.DateGankData): Boolean {
        return p0 == p1
    }

}