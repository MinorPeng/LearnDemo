package com.example.hp.slidingback.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hp.slidingback.R;
import com.example.hp.slidingback.adapter.NewsAdapter;
import com.example.hp.slidingback.bean.RecentChat;
import com.example.hp.slidingback.swipemenulistview.SwipeMenu;
import com.example.hp.slidingback.swipemenulistview.SwipeMenuCreator;
import com.example.hp.slidingback.swipemenulistview.SwipeMenuItem;
import com.example.hp.slidingback.swipemenulistview.SwipeMenuListView;

import java.util.LinkedList;

public class MainActivity extends Activity {

    private NewsAdapter adapter;
    private LinkedList<RecentChat> chats = new LinkedList<RecentChat>();
    private ListView listView;
    private SwipeMenuListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        chats.add(new RecentChat("章泽天", "点击进入手势返回的界面", "16:30", ""));
        chats.add(new RecentChat("宋茜", "点击进入SwipeBackLayout返回的界面", "17:30", ""));
        chats.add(new RecentChat("韩孝珠", "好好学习天天向上", "昨天", ""));
        chats.add(new RecentChat("景甜", "好好学习天天向上", "星期一", ""));
        chats.add(new RecentChat("刘亦菲", "好好学习天天向上", "17:30", ""));
        chats.add(new RecentChat("邓紫棋", "好好学习天天向上", "星期一", ""));
        chats.add(new RecentChat("finddreams",
                "http://blog.csdn.net/finddreams", "星期一", ""));

        initSwipeMenu();
    }

    /**
     * 初始化滑动菜单
     */
    private void initSwipeMenu() {
        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        adapter = new NewsAdapter(this, chats, listView);
        mListView.setAdapter(adapter);
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "up" item
                SwipeMenuItem upItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                upItem.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE8,
                        0xE9)));
                // set item width
                upItem.setWidth(dp2px(90));
                upItem.setIcon(R.drawable.up);
                menu.addMenuItem(upItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,
                                           int index) {

                switch (index) {
                    case 0:
                        break;
                    case 1:
                        chats.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                switch (position) {
                    case 0:
                        startActivity(GestureBackDemoActivity.class);
                        break;
                    case 1:
                        startActivity(SwipeBackDemoActivity.class);
                        break;
                }

            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_left);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
