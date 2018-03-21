package com.example.hp.douban.modules.main.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.douban.R;
import com.example.hp.douban.base.BaseSwipeBackActivity;
import com.example.hp.douban.http.RetrofitHelper;
import com.example.hp.douban.modules.movie.model.MovieSubject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by HP on 2017/5/15.
 */

public class SearchActivity extends BaseSwipeBackActivity implements View.OnClickListener{
    @Bind(R.id.search_edit)
    EditText searchEdit;
    @Bind(R.id.search_btn)
    Button searchButton;
    @Bind(R.id.movie_image_search)
    ImageView movieImage;
    @Bind(R.id.movie_title_search)
    TextView movieTitle;
    private RetrofitHelper mRetrofitHelper;
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mRetrofitHelper = new RetrofitHelper();
        search = searchEdit.getText().toString();
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                getMovie();
                break;
            default:
                break;
        }
    }

    private void getMovie() {
        Subscription subscription = mRetrofitHelper.getSearchMovie(search,"喜剧")
                .subscribe(new Action1<MovieSubject>() {
                    @Override
                    public void call(MovieSubject movieSubject) {
                        movieTitle.setText(movieSubject.subjects.get(0).title);
                        Glide.with(SearchActivity.this)
                                .load(movieSubject.subjects.get(0).images.small)
                                .into(movieImage);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscription(subscription);
    }
}
