package com.example.a14512.androiddevelopmentofartexploration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.a14512.androiddevelopmentofartexploration.activity.MessengerActivity;
import com.example.a14512.androiddevelopmentofartexploration.customView.SlidingMenu;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button messengerActivityBtn, shareButton, menuButton;
    private SlidingMenu slidingMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        slidingMenu = (SlidingMenu) findViewById(R.id.sliding_menu);
        messengerActivityBtn = (Button) findViewById(R.id.messengerActivityBtn);
        shareButton = (Button) findViewById(R.id.shareButton);
        menuButton = (Button) findViewById(R.id.change_menu);
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_id);
        mainLayout.setOnClickListener(this);
        messengerActivityBtn.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_id:
                slidingMenu.closeMenu();
                break;
            case R.id.messengerActivityBtn:
                startActivity(new Intent(this, MessengerActivity.class));
                break;
            case R.id.shareButton:
                showShare();
                break;
            case R.id.change_menu:
                slidingMenu.changeMenu();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
