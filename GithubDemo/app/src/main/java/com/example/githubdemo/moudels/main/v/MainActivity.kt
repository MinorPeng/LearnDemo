package com.example.githubdemo.moudels.main.v

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import com.example.githubdemo.R
import com.example.githubdemo.base.BaseActivity
import com.example.githubdemo.base.BaseItemClickListener
import com.example.githubdemo.common.utils.LogUtil
import com.example.githubdemo.moudels.User
import com.example.githubdemo.moudels.main.adapter.UserAdapter
import com.example.githubdemo.moudels.main.p.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), IMainView {

    private lateinit var mPresenter: MainPresenter
    private lateinit var mAdapter: UserAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private var mString: String? = null
    private var mLastVisibleItem = -1
    private var mPages = 1

    override fun initView() {
        mPresenter = MainPresenter(this, this)
        mAdapter = UserAdapter()
        mLinearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.adapter = mAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                LogUtil.e(localClassName, "scro $mLastVisibleItem ${mAdapter.itemCount} ${mAdapter.getDatas()?.size}")
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 1 == mAdapter.itemCount
                    && mString != null) {
                    mPresenter.getUserByPage(mString!!, mPages + 1)
                    mAdapter.loadMoreFooter()
                } else {
                    mAdapter.noMoreFooter()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition()
            }
        })

        mAdapter.setClickListener(object : BaseItemClickListener {
            override fun onClick(view: View, position: Int) {
                WebActivity.startActivity(this@MainActivity, mAdapter.getData(position)?.htmlUrl)
            }

            override fun onLongClick(view: View, position: Int) {

            }
        })

        swipeRefresh.setOnRefreshListener {
            if (mString == null) {
                toastMsg("search content can't be null!")
                swipeRefresh.isRefreshing = false
            } else {
                mPresenter.searchUser(mString!!)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 == null) {
                    toastMsg("search content can't be null!")
                } else {
                    mString = p0
                    mPresenter.searchUser(p0.toString())
                }
                return false
            }
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setAdapter(users: List<User>) {
        swipeRefresh.isRefreshing = false
        LogUtil.e(localClassName, "${users.size} ${users?.get(0)}")
        mAdapter.clear()
        mAdapter.addAll(users.toMutableList())
        LogUtil.e(localClassName, "${mAdapter.getDatas()?.size}")
    }

    override fun addUsers(users: List<User>) {
        mPages++
        mAdapter.addAll(users.toMutableList())
    }

    override fun showFooterView() {
        mAdapter.noMoreFooter()
    }
}
