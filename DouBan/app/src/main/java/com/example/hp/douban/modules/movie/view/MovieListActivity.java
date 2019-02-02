package com.example.hp.douban.modules.movie.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.hp.douban.R;
import com.example.hp.douban.base.BaseSwipeBackActivity;
import com.example.hp.douban.modules.movie.adpater.MovieAdapter;
import com.example.hp.douban.http.RetrofitHelper;
import com.example.hp.douban.modules.movie.model.MovieSubject;
import com.example.hp.douban.modules.movie.model.entity.USMovieSubject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

public class MovieListActivity extends BaseSwipeBackActivity {

    private String category, title;
    @Bind(R.id.movie_recycler_list)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar_list)
    Toolbar toolbar;
    @Bind(R.id.swipe_refresh_movie_list)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private MovieAdapter movieAdapter;
    private RetrofitHelper mRetrofitHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        category = getIntent().getStringExtra("category");
        title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        mRetrofitHelper = new RetrofitHelper();
        initView();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);
        getMovieList();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getMovieList() {
        switch (category) {
            case "us_box":
                Subscription subscriptionUs = mRetrofitHelper.getUsBoxMovie()
                        .subscribe(new Action1<USMovieSubject>() {
                            @Override
                            public void call(USMovieSubject usMovieSubject) {
                                movieAdapter.setUsBox(usMovieSubject.subjects);
                                movieAdapter.notifyDataSetChanged();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
                addSubscription(subscriptionUs);
                break;
            default:
                Subscription subscription = mRetrofitHelper.getMovie("重庆",0,20,category)
                        .subscribe(new Action1<MovieSubject>() {
                            @Override
                            public void call(MovieSubject movieSubject) {
                                movieAdapter.setMovies(movieSubject.subjects);
                                movieAdapter.notifyDataSetChanged();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
                addSubscription(subscription);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
