package com.example.a14512.swipeconflictdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyRecyclerView in_theaters_recycler_view;
    private MyRecyclerView coming_soon_recycler_view;
    private MyRecyclerView us_box_recycler_view;
    private MyRecyclerView top250_recycler_view;
    private MyRecyclerView weekly_recycler_view;
    private MyRecyclerView new_movies_recycler_view;
    private List<String> strings = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initRecyclerView();
        setAdapter();
    }

    private void setAdapter() {
        Adapter adapter = new Adapter();
        adapter.setStrings(strings);
        adapter.notifyDataSetChanged();
        in_theaters_recycler_view.setAdapter(adapter);
        coming_soon_recycler_view.setAdapter(adapter);
        us_box_recycler_view.setAdapter(adapter);
        top250_recycler_view.setAdapter(adapter);
        weekly_recycler_view.setAdapter(adapter);
        new_movies_recycler_view.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++ ) {
            strings.add("I am a item" + i);
        }
    }

    private void initView() {
        in_theaters_recycler_view = (MyRecyclerView) findViewById(R.id.in_theaters_recycler_view);
        coming_soon_recycler_view = (MyRecyclerView) findViewById(R.id.coming_soon_recycler_view);
        us_box_recycler_view = (MyRecyclerView) findViewById(R.id.us_box_recycler_view);
        top250_recycler_view = (MyRecyclerView) findViewById(R.id.top250_recycler_view);
        weekly_recycler_view = (MyRecyclerView) findViewById(R.id.weekly_recycler_view);
        new_movies_recycler_view = (MyRecyclerView) findViewById(R.id.new_movies_recycler_view);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this);
        layoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(this);
        layoutManager5.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager6 = new LinearLayoutManager(this);
        layoutManager6.setOrientation(LinearLayoutManager.HORIZONTAL);
        new_movies_recycler_view.setLayoutManager(layoutManager1);
        in_theaters_recycler_view.setLayoutManager(layoutManager2);
        coming_soon_recycler_view.setLayoutManager(layoutManager3);
        us_box_recycler_view.setLayoutManager(layoutManager4);
        top250_recycler_view.setLayoutManager(layoutManager5);
        weekly_recycler_view.setLayoutManager(layoutManager6);
    }
}
