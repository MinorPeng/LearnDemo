package com.example.hp.douban.modules.movie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.douban.R;
import com.example.hp.douban.base.BaseSwipeBackActivity;
import com.example.hp.douban.modules.movie.model.entity.MovieDetail;
import com.example.hp.douban.http.RetrofitHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

public class MovieActivity extends BaseSwipeBackActivity implements View.OnClickListener {

    @Bind(R.id.movie_image)
    ImageView movieImage;
    @Bind(R.id.movie_title_text)
    TextView movieTitle;
    @Bind(R.id.movie_detail_text)
    TextView movieText;
    @Bind(R.id.rate_text)
    TextView rateText;
    @Bind(R.id.toolbar_movie)
    Toolbar toolbar;
    private String id, title, detail;
    private RetrofitHelper mRetrofitHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("movie");
        Log.d("123456 id", ""+id);
        toolbar.setTitle(id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        mRetrofitHelper = new RetrofitHelper();
        getMovieDetail();
    }

    private void getMovieDetail() {
        Log.d("123465 get", "1");
        Subscription subscription = mRetrofitHelper.getMovieDetails(id)
                .subscribe(new Action1<MovieDetail>() {
            @Override
            public void call(MovieDetail movieDetail) {
                Log.d("123456 movie", ""+movieDetail);
                title = movieDetail.title;
                movieTitle.setText(title);
                detail = movieDetail.summary;
                Glide.with(MovieActivity.this).load(movieDetail.images.small).into(movieImage);
                movieText.setText("年代：" + movieDetail.year+"\n"
                        +"类型："+movieDetail.genres+"\n"
                        +"制片国家/地区："+movieDetail.countries);
                rateText.setText("评分：" + movieDetail.rating.average+"\n"
                        +"评星数："+movieDetail.rating.stars+"\n"
                        +"看过人数："+movieDetail.collect_count+"\n"
                        +"评分人数："+movieDetail.ratings_count);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        addSubscription(subscription);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_detail_text:
                Intent intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
                intent.putExtra("detail", detail);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
