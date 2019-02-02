package com.example.hp.douban.http;

import com.example.hp.douban.modules.movie.model.MovieSubject;
import com.example.hp.douban.modules.movie.model.entity.MovieDetail;
import com.example.hp.douban.modules.movie.model.entity.USMovieSubject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 定义一个接口，包含多个Api，简化代码
 * Created by HP on 2017/5/11.
 */

public interface ApiService {

    String BASE_URL = "https://api.douban.com/v2/";

    @GET("movie/top250")
    Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count") int count);

    @GET("movie/in_theaters")
    Observable<MovieSubject> getInTheaters(@Query("city") String city);

    @GET("movie/us_box")
    Observable<USMovieSubject> getUsBox();

    @GET("movie/weekly")
    Observable<MovieSubject> getWeekly();

    @GET("movie/new_movies")
    Observable<MovieSubject> getNewMovies();

    @GET("movie/coming_soon")
    Observable<MovieSubject> getComingSoon(@Query("start") int start, @Query("count") int count);

    @GET("movie/subject/{id}")
    Observable<MovieDetail> getMovieDetail(@Path("id") String id);

    @GET("movie/search")
    Observable<MovieSubject> searchMovie(@Query("q") String q, @Query("tag") String tag,
                                         @Query("start") int start, @Query("count") int count);
}
