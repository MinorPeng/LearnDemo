package com.example.a14512.mvp_login.Bean;

/**
 * Created by 14512 on 2017/7/1.
 */

public class UserBean {
    private String mFirstName;
    private String mLastName;

    public UserBean(String mFirstName, String mLastName) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }
}
