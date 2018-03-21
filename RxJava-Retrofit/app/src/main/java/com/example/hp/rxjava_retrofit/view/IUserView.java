package com.example.hp.rxjava_retrofit.view;

/**
 * Created by HP on 2017/5/4.
 * 请求过程进行封装
 */

public interface IUserView {

    int getID();

    String getUserName();

    String getPwd();

    void setUserName(String userName);

    void setPwd(String pwd);

}
