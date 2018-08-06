package com.example.a14512.recyclerviewandlistviewdemo.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.a14512.recyclerviewandlistviewdemo.R;
import com.example.a14512.recyclerviewandlistviewdemo.adapter.ContactsAdapter;
import com.example.a14512.recyclerviewandlistviewdemo.adapter.OnItemClickListener1;
import com.example.a14512.recyclerviewandlistviewdemo.data.Contact;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/4/29
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private ContactsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Contact> mContacts = new ArrayList<>();
    private ArrayList<Contact> mAddData;
    private int lastVisibleItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        getData();
    }

    private void getData() {
        initContacts(15);
        mAdapter = new ContactsAdapter(mContacts);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //上拉加载，设置滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==
                        mAdapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //把要刷新的实例化
                            initContacts(2);
                            mAdapter.upAddData(mAddData);
                            Toast.makeText(RecyclerViewActivity.this, "更新了2条数据...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    },1000);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //找到最后一个的位置
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        //设置监听
        mAdapter.setOnItemClickListener(new OnItemClickListener1() {
            @Override
            public void onItemClick(View view, int position, String str) {
                Toast.makeText(RecyclerViewActivity.this, "click" + str, Toast.LENGTH_SHORT).show();
                DetailActivity.actionStart(RecyclerViewActivity.this, mContacts.get(position));
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //把要刷新的实例化
                        initContacts(3);
                        mAdapter.swipeAddData(mAddData);
                        Toast.makeText(RecyclerViewActivity.this, "更新了三条数据...",
                                Toast.LENGTH_SHORT).show();
                    }
                },1000);
                //刷新结束
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.swipeRefreshView);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //瀑布流布局
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        //设置刷新图标的颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    private void initContacts(int n) {
        mAddData = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (n == 2) {
                Contact contact = new Contact(i + "上拉 分享家", R.mipmap.ic_launcher_round,
                        "  content:" + i + "上拉 分享家");
                mAddData.add(contact);
            }else if (n == 3) {
                Contact contacts = new Contact("下拉 分享家" + i, R.mipmap.ic_launcher_round,
                        "  content:" + i + "下拉 分享家");
                mAddData.add(contacts);
            }else{
                Contact contacts = new Contact("分享家" + i, R.mipmap.ic_launcher_round,
                        "  content:" + i + "分享家");
                mContacts.add(contacts);
            }
        }
    }
}
