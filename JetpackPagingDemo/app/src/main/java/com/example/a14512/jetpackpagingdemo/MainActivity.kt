package com.example.a14512.jetpackpagingdemo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private val mViewModel by lazy { ViewModelProviders.of(this).get(KituViewModel::class.java) }

    private lateinit var mStudentKituViewModel: KituViewModel
    private lateinit var mAdapter: KituPagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSearchFiled()
        searchForResults("Android")
        mStudentKituViewModel=getViewModel()
        initAdapter()
        initSwipeToRefresh()
        mStudentKituViewModel.showDatas("")
    }

    private fun getViewModel(): KituViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = KituDataRepository(Executor {  })
                return KituViewModel(application, repo) as T
            }
        })[KituViewModel::class.java]
    }
    private fun initSwipeToRefresh() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW)
        swipeRefreshLayout.setOnRefreshListener {
            mStudentKituViewModel.refresh()
        }
        mStudentKituViewModel.refreshState.observe(this, Observer { result->
            if (result==null){
                return@Observer
            }
            when(result.status){
                Status.LOADING->{
                    swipeRefreshLayout.isRefreshing=true
                }
                Status.SUCCESS->{
                    swipeRefreshLayout.isRefreshing=false
                }
                Status.ERROR->{
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    swipeRefreshLayout.isRefreshing=false
                }
            }
        })
    }
    private fun initAdapter() {
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayout.VERTICAL
        mainRecyclerView.layoutManager = mLayoutManager
        mainRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))//添加分割线
        mAdapter= KituPagedListAdapter()
        mainRecyclerView.adapter = mAdapter
        mStudentKituViewModel.posts.observe(this, Observer((mAdapter::submitList)))
    }

    private fun initSearchFiled() {
        //TODO ListAdapter与RecyclerView的包冲突
    }


    private fun searchForResults(queryFilter: String) {
//        Log.e("activity", mViewModel.all.value?.size?.toString())
//        initKitu()
    }

    private fun initKitu() {
//        val adapter = KituPagedListAdapter()
//        mainRecyclerView.adapter = adapter
//        mViewModel.all.observe(this, Observer(adapter::setList))
    }

}
