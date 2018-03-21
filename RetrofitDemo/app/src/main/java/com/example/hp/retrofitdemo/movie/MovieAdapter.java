package com.example.hp.retrofitdemo.movie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.retrofitdemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by HP on 2017/4/10.
 */

public class MovieAdapter extends RecyclerView.Adapter {
    private List<Movie> mMovies;

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieHolder(LayoutInflater.from(parent.
                getContext()).inflate(R.layout.movie_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        MovieHolder movieHolder = (MovieHolder) holder;
        //需要建一个MovieApplication类继承Application来初始化ImageLoader，
        // 并且要在Mainfest中进行全局注册，修改名称，没有初始化会崩溃
        ImageLoader.getInstance().displayImage(movie.images.small, movieHolder.mImageView);
        movieHolder.time.setText("上映时间：" + movie.year + "年");
        movieHolder.title.setText(movie.title);
        movieHolder.subTitle.setText(movie.original_title);

    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    private class MovieHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView title;
        public TextView subTitle;
        public TextView time;
        public MovieHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_image);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            subTitle = (TextView) itemView.findViewById(R.id.movie_sub_title);
            time = (TextView) itemView.findViewById(R.id.movie_time);
        }
    }
}
