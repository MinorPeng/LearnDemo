package com.example.a14512.customviewdemo.retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author PHS1024
 * @date 2019/6/2 15:22
 */
public interface ApiService {
    public final static String BASE_URL = "http://";

    @GET("user")
    Call<User> getUser();
}
