package com.example.a14512.recycleradapterdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var data: ArrayList<HomeItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    private fun initView() {
        Log.e("123", ""+data.size)
        val recycler: RecyclerView = recyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.layoutManager = layoutManager
        val adapter = RecyclerAdapter(R.layout.item_recycler_view, data)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun initData() {
        var n = 0
        do {
            var item = HomeItem()
            item.title = "name$n"
            item.img = R.mipmap.ic_launcher
            data.add(item)
            n++
            if (n == 10) {
                break
            }
        } while (true)
    }
}

class RecyclerAdapter(layoutId: Int, data: List<HomeItem>) :
        BaseQuickAdapter<HomeItem, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder, item: HomeItem) {
        helper.setText(R.id.tvRecycler, item.title)
        helper.setImageResource(R.id.imgRecycler, item.img)
    }
}

class HomeItem : Serializable {
    var title: String = ""
    var img: Int = 0
}
