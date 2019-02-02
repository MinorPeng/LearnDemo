package com.example.hp.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hp.retrofitdemo.gank.GankActivity;
import com.example.hp.retrofitdemo.movie.MovieActivity;

/**
 * Created by HP on 2017/4/9.
 */

public class HomeActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        findViewById(R.id.movie_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MovieActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.award_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GankActivity.class);
                startActivity(intent);
            }
        });
    }
}
