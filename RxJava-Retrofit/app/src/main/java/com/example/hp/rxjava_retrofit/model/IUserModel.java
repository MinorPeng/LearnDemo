package com.example.hp.rxjava_retrofit.model;

import com.example.hp.rxjava_retrofit.bean.UserBean;

import java.util.List;

/**
 * Created by HP on 2017/5/4.
 */

public interface IUserModel {

    void setUserName(String userName);

    void setPwd(String pwd);

    int getID();

    void setId(int id);

    UserBean load(int id);  //通过id读取user信息，返回一个UserBean

    List<UserBean> save(int id, String username, String pwd);  //通过id保存user信息，返回一个UserBean
}
