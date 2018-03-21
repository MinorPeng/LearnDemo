package com.example.hp.rxjava_retrofit.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.rxjava_retrofit.R;
import com.example.hp.rxjava_retrofit.base.BaseActivity;
import com.example.hp.rxjava_retrofit.bus.RefreshMessage;
import com.example.hp.rxjava_retrofit.bus.RxBus;
import com.example.hp.rxjava_retrofit.presenter.UserPresenter;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements IUserView {

    UserPresenter presenter;
    @Bind(R.id.id)
    EditText id;
    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.save)
    Button save;
    @Bind(R.id.load)
    Button load;
    @Bind(R.id.testRetrofit)
    Button testRetrofit;
    Subscription rxbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPresenter();
        Log.d("mainactivity", "1");
        lisenterBus();
        Log.d("mainactivity", "2");
    }

    private void lisenterBus() {
        rxbus = RxBus.getInstance().toObserverable(RefreshMessage.class)
                .subscribe(new Action1<RefreshMessage>() {
                    @Override
                    public void call(RefreshMessage refreshMessage) {
                        Toast.makeText(MainActivity.this, refreshMessage.getName() + refreshMessage.getId(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initPresenter() {
        presenter = new UserPresenter(this);
    }


    @Override
    public int getID() {
        Log.d("mainactivity", "id");
        return new Integer(id.getText().toString());
    }

    @Override
    public String getUserName() {
        Log.d("mainactivity", "name");
        return username.getText().toString();
    }

    @Override
    public String getPwd() {
        Log.d("mainactivity", "pwd");
        return pwd.getText().toString();
    }

    @Override
    public void setUserName(String userName) {
        Log.d("mainactivity", "set");
        username.setText(userName);
    }

    @Override
    public void setPwd(String pwd) {
        Log.d("mainactivity", "set pwd");
        this.pwd.setText(pwd);
    }

    @OnClick({R.id.save, R.id.load, R.id.testRetrofit})
    public void onViewClicked(View view) {
        Log.d("mainactivity", "click");
        switch (view.getId()) {
            case R.id.save:
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                presenter.saveUser(getID(), getUserName(), getPwd());
                presenter.loading(MainActivity.this);
                break;
            case R.id.load:
                Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
                presenter.loadUser(getID());
                break;
            case R.id.testRetrofit:
                Toast.makeText(this, "retrofit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RetrofitActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (!rxbus.isUnsubscribed()) {
            rxbus.unsubscribe();
        }
        super.onDestroy();
    }
}