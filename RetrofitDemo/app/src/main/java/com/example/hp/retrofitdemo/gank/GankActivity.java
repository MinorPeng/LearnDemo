package com.example.hp.retrofitdemo.gank;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.hp.retrofitdemo.BaseActivity;
import com.example.hp.retrofitdemo.R;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by HP on 2017/4/9.
 */

public class GankActivity extends BaseActivity {

    private GankLoader mGankLoader;
    private RecyclerView mRecyclerView;
    private GankAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gank_activity);
        mGankLoader = new GankLoader();
        initView();
        Log.d("GankActivity", "gank");
        getGankList();
    }

    private void getGankList() {
        Subscription subcription = mGankLoader.getGankList().subscribe(new Action1<List<GankEntry>>() {
            @Override
            public void call(List<GankEntry> gankEntries) {
                Log.i("FK","gank size:"+gankEntries.size());
                Log.d("GankActivity", "ganklist call");
                mAdapter.setData(gankEntries);
                mAdapter.notifyDataSetChanged();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        addSubscription(subcription);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(R.string.award_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.gank_recycler_view);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        Log.d("GankActivity", "manager");
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        mAdapter = new GankAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 20, 20);
        }
    }
}
