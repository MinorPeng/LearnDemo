package com.example.hp.douban.modules.main.view;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hp.douban.R;
import com.example.hp.douban.modules.movie.view.MovieListActivity;
import com.example.hp.douban.modules.movie.adpater.MovieAdapter;
import com.example.hp.douban.http.RetrofitHelper;
import com.example.hp.douban.modules.movie.model.MovieSubject;
import com.example.hp.douban.modules.movie.model.entity.USMovieSubject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by HP on 2017/5/11.
 */

public class MovieFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.next_text1)
    TextView nextText1;
    @Bind(R.id.next_text2)
    TextView nextText2;
    @Bind(R.id.next_text3)
    TextView nextText3;
    @Bind(R.id.next_text4)
    TextView nextText4;
    @Bind(R.id.next_image1)
    ImageView nextImage1;
    @Bind(R.id.next_image2)
    ImageView nextImage2;
    @Bind(R.id.next_image3)
    ImageView nextImage3;
    @Bind(R.id.next_image4)
    ImageView nextImage4;

    @Bind(R.id.movie_title_text1)
    TextView movieTitleText1;
    @Bind(R.id.movie_title_text2)
    TextView movieTitleText2;
    @Bind(R.id.movie_title_text3)
    TextView movieTitleText3;
    @Bind(R.id.movie_title_text4)
    TextView usMovieTitleText;

    @Bind(R.id.movie_recycler_in_theaters)
    RecyclerView mRecyclerViewInTheaters;
    @Bind(R.id.movie_recycler_top250)
    RecyclerView mRecyclerViewTop250;
    @Bind(R.id.movie_recycler_coming_soon)
    RecyclerView mRecyclerViewComingSoon;
    @Bind(R.id.movie_recycler_us_box)
    RecyclerView mRecyclerViewUsBox;

    @Bind(R.id.swipe_refresh_movie_frag)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.movie_scroll_layout)
    ScrollView movieScrollLayout;

    private RetrofitHelper mRetrofitHelper;
    private List<RecyclerView> mRecyclerViewList = new ArrayList<>();
    private List<String> mCategoryList = new ArrayList<>();
    private List<MovieAdapter> mMovieAdapterList = new ArrayList<>();
    private String city = "重庆";
    private List<TextView> mTextViewList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this,view);
        mRetrofitHelper = new RetrofitHelper();
        movieScrollLayout.setVisibility(View.INVISIBLE);
        initView();
        nextText1.setOnClickListener(this);
        nextText2.setOnClickListener(this);
        nextText3.setOnClickListener(this);
        nextText4.setOnClickListener(this);
        nextImage1.setOnClickListener(this);
        nextImage2.setOnClickListener(this);
        nextImage3.setOnClickListener(this);
        nextImage4.setOnClickListener(this);
        return view;
    }

    private void initView() {
        mTextViewList.add(movieTitleText1);
        mTextViewList.add(movieTitleText2);
        mTextViewList.add(movieTitleText3);
        mCategoryList.add("in_theaters");
        mCategoryList.add("coming_soon");
        mCategoryList.add("top250");
        mRecyclerViewList.add(mRecyclerViewInTheaters);
        mRecyclerViewList.add(mRecyclerViewComingSoon);
        mRecyclerViewList.add(mRecyclerViewTop250);
        mRecyclerViewList.add(mRecyclerViewUsBox);

        for (int i=0; i<mRecyclerViewList.size(); i++) {
            MovieAdapter mMovieAdapter = new MovieAdapter();
            mMovieAdapterList.add(mMovieAdapter);
        }
        for (int i=0; i<mRecyclerViewList.size(); i++) {
            RecyclerView recyclerView = mRecyclerViewList.get(i);
            recyclerView.addItemDecoration(new MovieDecoration());
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(mMovieAdapterList.get(i));
        }
        getMovieList();
        //刷新
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 获取电影列表
     */
    private void getMovieList() {
        for (int i=0; i<mCategoryList.size(); i++) {
            final MovieAdapter movieAdapter = mMovieAdapterList.get(i);
            final TextView textView = mTextViewList.get(i);
            Subscription subscription = mRetrofitHelper.getMovie(city,1,10, mCategoryList.get(i))
                    .subscribe(new Action1<MovieSubject>() {
                        @Override
                        public void call(MovieSubject movieSubject) {
                            String title = movieSubject.title;
                            textView.setText(title);
                            movieAdapter.setMovies(movieSubject.subjects);
                            movieAdapter.notifyDataSetChanged();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
        }

        mRetrofitHelper.getUsBoxMovie()
                .subscribe(new Action1<USMovieSubject>() {
                    @Override
                    public void call(USMovieSubject usMovieSubject) {
                        usMovieTitleText.setText(usMovieSubject.title);
                        mMovieAdapterList.get(3).setUsBox(usMovieSubject.subjects);
                        mMovieAdapterList.get(3).notifyDataSetChanged();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        movieScrollLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_image1:
            case R.id.next_text1:
                ActionStart("in_theaters", movieTitleText1.getText().toString());
                break;
            case R.id.next_image2:
            case R.id.next_text2:
                ActionStart("coming_soon", movieTitleText2.getText().toString());
                break;
            case R.id.next_image3:
            case R.id.next_text3:
                ActionStart("top250", movieTitleText3.getText().toString());
                break;
            case R.id.next_image4:
            case R.id.next_text4:
                ActionStart("us_box", "");
                break;
            default:
                break;
        }
    }

    private class MovieDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 20);
        }
    }

    private void ActionStart(String category, String title) {
        Intent intent = new Intent(getActivity(), MovieListActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}

