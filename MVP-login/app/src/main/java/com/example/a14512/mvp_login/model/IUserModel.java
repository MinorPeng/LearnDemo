package com.example.a14512.mvp_login.model;

import com.example.a14512.mvp_login.Bean.UserBean;

/**
 * Created by 14512 on 2017/7/1.
 */

public interface IUserModel {
    void setID(int id);

    void setFistName(String firstName);

    void setLastName(String lastName);

    UserBean load(int id);
}
