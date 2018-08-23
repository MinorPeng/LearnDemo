package com.example.a14512.jetpackpagingdemo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.a14512.jetpackpagingdemo.pagingdemo.Status
import com.example.a14512.jetpackpagingdemo.pagingdemo.StuAdapter
import com.example.a14512.jetpackpagingdemo.pagingdemo.StuDataRepository
import com.example.a14512.jetpackpagingdemo.pagingdemo.StuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var mStuViewModel: StuViewModel
    private lateinit var mAdapter: StuAdapter
    private lateinit var mRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mStuViewModel = getViewModel()
        initAdapter()
        initRefreshLayout()
        mStuViewModel.showDatas("qwe")
    }

    private fun getViewModel(): StuViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                Log.d("debug", "create")
                val repo = StuDataRepository(Executors.newFixedThreadPool(5))
                return StuViewModel(application, repo) as T
            }
        })[StuViewModel::class.java]
    }

    private fun initRefreshLayout() {
        mRefreshLayout = swipe_Refresh_layout
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW)
        mRefreshLayout.setOnRefreshListener {
            mStuViewModel.refresh()
        }
        mStuViewModel.refreState.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            when(it.status) {
                Status.LOADING -> { mRefreshLayout.isRefreshing = true }
                Status.SUCCESS -> { mRefreshLayout.isRefreshing = false }
                Status.ERROR -> {
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    mRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun initAdapter() {
        mAdapter = StuAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = main_recycler_view
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = mAdapter
        mStuViewModel.posts.observe(this, Observer((mAdapter::submitList)))
    }


}
