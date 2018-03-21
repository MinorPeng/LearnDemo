package com.example.a14512.mvp_login.presenter;

import com.example.a14512.mvp_login.Bean.UserBean;
import com.example.a14512.mvp_login.model.IUserModel;
import com.example.a14512.mvp_login.model.UserModel;
import com.example.a14512.mvp_login.view.IUserView;

/**
 * Created by 14512 on 2017/7/1.
 */

public class Userpresenter {
    private IUserView mUserView;
    private IUserModel mUserModel;

    public Userpresenter(IUserView view) {
        mUserView = view;
        mUserModel = new UserModel();
    }

    public void saveUser(int id, String firstName, String lastName) {
        mUserModel.setID(id);
        mUserModel.setFistName(firstName);
        mUserModel.setLastName(lastName);
    }

    public void loadUser(int id) {
        UserBean userBean = mUserModel.load(id);
        mUserView.setFirstName(userBean.getmFirstName());
        mUserView.setLastName(userBean.getmLastName());
    }
}
