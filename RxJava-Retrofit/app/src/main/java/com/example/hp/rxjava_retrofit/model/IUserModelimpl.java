package com.example.hp.rxjava_retrofit.model;

import com.example.hp.rxjava_retrofit.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/5/4.
 */

public class IUserModelimpl implements IUserModel {

    private List<UserBean> userBeans = new ArrayList<UserBean>();

    @Override
    public void setUserName(String userName) {

    }

    @Override
    public void setPwd(String pwd) {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public UserBean load(int id) {
        UserBean userBean = null;
        if (userBeans.size() > 0) {
            for (int i=0; i < userBeans.size(); i++) {
                if (userBeans.get(i).getId() == id) {
                    userBean = new UserBean(userBeans.get(i).getId(),
                            userBeans.get(i).getName(),
                            userBeans.get(i).getPwd());
                }
            }
        }else {
            userBean = new UserBean(-1, "", "");
        }
        return userBean;
    }

    @Override
    public List<UserBean> save(int id, String username, String pwd) {
        UserBean userBean = new UserBean(id, username, pwd);
        userBeans.add(userBean);
        return userBeans;
    }
}
