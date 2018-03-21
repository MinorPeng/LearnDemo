package com.example.hp.douban.http;

import android.util.Log;

import com.example.hp.douban.modules.movie.model.MovieSubject;
import com.example.hp.douban.modules.movie.model.entity.MovieDetail;
import com.example.hp.douban.modules.movie.model.entity.USMovieSubject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by HP on 2017/5/11.
 */

public class RetrofitHelper extends RxUtil {
    private ApiService mApiService;

    public RetrofitHelper() {
        mApiService = RetrofitServiceManager
                .getInstance()
                .create(ApiService.class);
    }

    /**
     * 获取电影列表
     * @param start
     * @param count
     * @return
     */
    public Observable<MovieSubject> getMovie(String city, int start, int count, String category) {
        switch (category) {
            case "in_theaters":
                return observe(mApiService.getInTheaters(city))
                        .map(new Func1<MovieSubject, MovieSubject>() {
                            @Override
                            public MovieSubject call(MovieSubject movieSubject) {
                                return movieSubject;
                            }
                        });
            case "coming_soon":
                return observe(mApiService.getComingSoon(start, count))
                        .map(new Func1<MovieSubject, MovieSubject>() {
                            @Override
                            public MovieSubject call(MovieSubject movieSubject) {
                                return movieSubject;
                            }
                        });
            case "us_box":

            case "weekly":

            case "new_movies":

            //case "top250":
            default:
                return observe(mApiService.getTop250(start, count))
                        .map(new Func1<MovieSubject, MovieSubject>() {
                            @Override
                            public MovieSubject call(MovieSubject movieSubject) {
                                return movieSubject;
                            }
                        });
        }

    }

    public Observable<USMovieSubject> getUsBoxMovie() {
        return observe(mApiService.getUsBox())
                .map(new Func1<USMovieSubject, USMovieSubject>() {
                    @Override
                    public USMovieSubject call(USMovieSubject usMovieSubject) {
                        return usMovieSubject;
                    }
                });
    }

    public Observable<MovieDetail> getMovieDetails(String id) {
        return observe(mApiService.getMovieDetail(id))
                .map(new Func1<MovieDetail, MovieDetail>() {
                    @Override
                    public MovieDetail call(MovieDetail movieDetail) {
                        Log.d("123456","loader"+movieDetail);
                        return movieDetail;
                    }
                });
    }

    public Observable<MovieSubject> getSearchMovie(String q, String tag) {
        return observe(mApiService.searchMovie(q, tag, 0, 5))
                .map(new Func1<MovieSubject, MovieSubject>() {
                    @Override
                    public MovieSubject call(MovieSubject movieSubject) {
                        Log.d("123456","loader search"+movieSubject);
                        return movieSubject;
                    }
                });
    }

}
