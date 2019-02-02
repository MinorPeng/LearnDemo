package com.example.hp.douban.modules.movie.view;

import android.os.Bundle;
import android.widget.TextView;

import com.example.hp.douban.R;
import com.example.hp.douban.base.BaseSwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailActivity extends BaseSwipeBackActivity {

    @Bind(R.id.movie_detail_text_frag)
    TextView movieDetailFragText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        movieDetailFragText.setText(getIntent().getStringExtra("detail"));
    }

}
