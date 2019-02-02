package com.example.hp.rxjava_retrofit.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.hp.rxjava_retrofit.bean.UserBean;
import com.example.hp.rxjava_retrofit.model.IUserModel;
import com.example.hp.rxjava_retrofit.model.IUserModelimpl;
import com.example.hp.rxjava_retrofit.view.IUserView;

/**
 * Created by HP on 2017/5/4.
 */

public class UserPresenter {

    private IUserView mIUserView;
    private IUserModel mIUserModel;

    public UserPresenter(IUserView mUserView) {
        this.mIUserView = mUserView;
        this.mIUserModel = new IUserModelimpl();
    }

    public void saveUser(int id, String username, String pwd) {
        mIUserModel.setId(id);
        mIUserModel.setUserName(username);
        mIUserModel.setPwd(pwd);
        mIUserModel.save(id, username, pwd);
    }

    public void loadUser(int id) {
        UserBean bean = mIUserModel.load(id);
        mIUserView.setUserName(bean.getName());  //通过调用IUserView方法来更新显示
        mIUserView.setPwd(bean.getPwd());
    }

    public void loading(Context context) {
        final ProgressDialog dialog = ProgressDialog.show(context, "登录中···", "正在登录···");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    dialog.cancel();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
