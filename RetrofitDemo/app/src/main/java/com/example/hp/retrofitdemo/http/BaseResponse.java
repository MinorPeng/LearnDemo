package com.example.hp.retrofitdemo.http;

/**
 * Created by HP on 2017/4/9.
 */

public class BaseResponse<T> {
    public int status;
    public String message;

    public T data;

    public boolean isSuccess(){
        return status == 200;
    }
}
