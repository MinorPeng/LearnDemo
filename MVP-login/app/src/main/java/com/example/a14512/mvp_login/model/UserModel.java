package com.example.a14512.mvp_login.model;

import android.util.SparseArray;

import com.example.a14512.mvp_login.Bean.UserBean;

/**
 * Created by 14512 on 2017/7/1.
 */

public class UserModel implements IUserModel {

    private String mFirstName;
    private String mLastName;
    private int mID;
    private SparseArray<UserBean> mUsererArray = new SparseArray<UserBean>();

    @Override
    public void setID(int id) {
        mID = id;
    }

    @Override
    public void setFistName(String firstName) {
        mFirstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        mLastName = lastName;
        UserBean userBean = new UserBean(mFirstName, mLastName);
        mUsererArray.append(mID, userBean);
    }

    @Override
    public UserBean load(int id) {
        mID = id;
        UserBean userBean = mUsererArray.get(mID, new UserBean("not found", "not found"));
        return userBean;
    }
}
