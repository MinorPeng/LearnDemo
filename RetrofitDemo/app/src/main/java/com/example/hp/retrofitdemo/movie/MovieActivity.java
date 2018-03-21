package com.example.hp.retrofitdemo.movie;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.hp.retrofitdemo.R;
import com.example.hp.retrofitdemo.http.Fault;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by HP on 2017/4/9.
 */

public class MovieActivity extends AppCompatActivity implements View.OnClickListener {
    private MovieLoader mMovieLoader;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mMovieLoader = new MovieLoader();  //创建Loader实例
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(R.string.movie_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new MovieDecoration());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mMovieAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mMovieAdapter);
        getMovieList();
    }

    /**
     * 获取电影列表
     */
    private void getMovieList() {
        mMovieLoader.getMovie(0,10).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                mMovieAdapter.setMovies(movies);
                mMovieAdapter.notifyDataSetChanged();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //统一处理错误
                Log.e("TAG", "error message:" +throwable.getMessage());
                if (throwable instanceof Fault) {
                    Fault fault = (Fault) throwable;
                    if (fault.getErrorCode() == 400) {
                        //错误处理
                    }else if (fault.getErrorCode() == 500) {
                        //错误处理
                    }else if (fault.getErrorCode() == 501) {
                        //错误处理
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        //getMovieRx();
    }

    private class MovieDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 20);
        }
    }
}
