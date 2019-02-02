package com.example.hp.retrofitdemo.movie;

import com.example.hp.retrofitdemo.http.ObjectLoader;
import com.example.hp.retrofitdemo.http.RetrofitServiceManager;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func1;

/**
 * 把请求逻辑封装在一个业务Loader里，处理多个Api接口
 * Created by HP on 2017/4/10.
 */

public class MovieLoader extends ObjectLoader{
    private MovieService mMovieService;

    public MovieLoader() {
        mMovieService = RetrofitServiceManager.getInstance().create(MovieService.class);
    }

    /**
     * 获取电影列表
     * @param start
     * @param count
     * @return
     */
    public Observable<List<Movie>> getMovie(int start, int count) {
        return observe(mMovieService.getTop250(start, count))
                //封装统一处理对返回结果的判断出现问题
//                .map(new PayLoad<BaseResponse<List<Movie>>>());
                .map(new Func1<MovieSubject, List<Movie>>() {
                    @Override
                    public List<Movie> call(MovieSubject movieSubject) {
                        return movieSubject.subjects;
                    }
                });
    }


    public Observable<String> getWeatherList(String cityId, String key) {
        return observe(mMovieService.getWeather(cityId, key))
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return null;
                    }
                });
    }
    //定义一个接口，包含多个Api，简化代码
    public interface MovieService{
        //获取豆瓣Top250 榜单
        @GET("top250")
        Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count")int count);

        @FormUrlEncoded
        @POST("/x3/weather")
        Observable<String> getWeather(@Field("cityId") String cityId, @Field("key") String key);
    }
}
