package com.example.a14512.recyclerviewandlistviewdemo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a14512.recyclerviewandlistviewdemo.R;
import com.example.a14512.recyclerviewandlistviewdemo.adapter.ListAdapter;
import com.example.a14512.recyclerviewandlistviewdemo.data.Contact;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/4/29
 */
public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private SwipeRefreshLayout swipeRefresh;
    private ListAdapter mAdapter;
    private ArrayList<Contact> mContacts = new ArrayList<>();
    private ArrayList<Contact> mAddData;
    private int lastVisibleItem;

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, ListViewActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initView();
        getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getData() {
        initContacts(15);
        mAdapter = new ListAdapter(mContacts);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        View footerView = LayoutInflater.from(this).inflate(R.layout.item_footer_view, null);
        listView.addFooterView(footerView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailActivity.actionStart(ListViewActivity.this, mContacts.get(position));
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && lastVisibleItem ==
                        mAdapter.getCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //把要刷新的实例化
                            initContacts(2);
                            mAdapter.upAddData(mAddData);
                            Toast.makeText(ListViewActivity.this, "更新了2条数据...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    },1000);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisibleItem = view.getLastVisiblePosition();
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //把要刷新的实例化
                        initContacts(3);
                        mAdapter.swipeAddData(mAddData);
                        Toast.makeText(ListViewActivity.this, "更新了三条数据...",
                                Toast.LENGTH_SHORT).show();
                    }
                },1000);
                //刷新结束
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void initView() {
        listView = findViewById(R.id.listView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
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
