package com.example.hp.mywork;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Fragment
    private MessageFragment mMessageFragment;
    private ContactsFragment mContactsFragment;
    private NewsFragment mNewsFragment;
    private SettingFragment mSettingFragment;
    //布局
    private View messageLayout;
    private View contactsLayout;
    private View newsLayout;
    private View settingLayout;
    //显示图标的控件
    private ImageView messageImage;
    private ImageView contactsImage;
    private ImageView newsImage;
    private ImageView settingImage;
    //显示消息内容的控件
    private TextView messageText;
    private TextView contactsText;
    private TextView newsText;
    private TextView settingText;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化布局元素
        initViews();
        mFragmentManager = getSupportFragmentManager();
        //第一次启动时选中第0个
        setTabSelection(0);

    }
    //获取控件的实例
    private void initViews() {
        messageLayout = findViewById(R.id.message_layout);
        contactsLayout = findViewById(R.id.contacts_layout);
        newsLayout = findViewById(R.id.news_layout);
        settingLayout = findViewById(R.id.setting_layout);

        messageImage = (ImageView) findViewById(R.id.message_image);
        contactsImage = (ImageView) findViewById(R.id.contacts_image);
        newsImage = (ImageView) findViewById(R.id.news_image);
        settingImage = (ImageView) findViewById(R.id.setting_image);

        messageText = (TextView) findViewById(R.id.message_text);
        contactsText = (TextView) findViewById(R.id.contacts_text);
        newsText = (TextView) findViewById(R.id.news_text);
        settingText = (TextView) findViewById(R.id.setting_text);
        //设置点击事件
        messageLayout.setOnClickListener(this);
        contactsLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_layout:
                setTabSelection(0);
                break;
            case R.id.contacts_layout:
                setTabSelection(1);
                break;
            case R.id.news_layout:
                setTabSelection(2);
                break;
            case R.id.setting_layout:
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);  //隐藏所有的Fragment，防止多个显示
        switch (index) {
            case 0:
                messageImage.setImageResource(R.drawable.message_click);
                messageText.setTextColor(Color.BLUE);  //当点击控件时，改变文字颜色
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    transaction.add(R.id.content, mMessageFragment);
                }else {
                    transaction.show(mMessageFragment);
                }
                break;
            case 1:
                contactsImage.setImageResource(R.drawable.contacts_click);
                contactsText.setTextColor(Color.BLUE);
                if (mContactsFragment == null) {
                    mContactsFragment = new ContactsFragment();
                    transaction.add(R.id.content, mContactsFragment);
                }else {
                    transaction.show(mContactsFragment);
                }
                break;
            case 2:
                newsImage.setImageResource(R.drawable.news_click);
                newsText.setTextColor(Color.BLUE);
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment();
                    transaction.add(R.id.content, mNewsFragment);
                }else {
                    transaction.show(mNewsFragment);
                }
                break;
            case 3:
                settingImage.setImageResource(R.drawable.setting_click);
                settingText.setTextColor(Color.BLUE);
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                    transaction.add(R.id.content, mSettingFragment);
                }else {
                    transaction.show(mSettingFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }
    //将所有的Fragment都置为隐藏状态
    private void hideFragments(FragmentTransaction transaction) {
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }
        if (mContactsFragment != null) {
            transaction.hide(mContactsFragment);
        }
        if (mNewsFragment != null) {
            transaction.hide(mNewsFragment);
        }
        if (mSettingFragment != null) {
            transaction.hide(mSettingFragment);
        }
    }
    //清除所有选中的状态
    private void clearSelection() {
        messageImage.setImageResource(R.drawable.message);
        messageText.setTextColor(Color.WHITE);
        contactsImage.setImageResource(R.drawable.contacts);
        contactsText.setTextColor(Color.WHITE);
        newsImage.setImageResource(R.drawable.news);
        newsText.setTextColor(Color.WHITE);
        settingImage.setImageResource(R.drawable.setting);
        settingText.setTextColor(Color.WHITE);
    }
}
