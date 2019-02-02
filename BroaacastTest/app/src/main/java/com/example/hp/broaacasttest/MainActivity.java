package com.example.hp.broaacasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author 14512
 *
 * */
public class MainActivity extends AppCompatActivity {

    private IntentFilter mIntentFilter;
    private NetworkChangeReceiver mNetworkChangeReceiver;

    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntentFilter = new IntentFilter();
        //添加广播想要监听的类型，监听网络状态是否发生变化
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        //注册广播
        registerReceiver(mNetworkChangeReceiver, mIntentFilter);

        //获取本地实例
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        Button saveButton = (Button) findViewById(R.id.send_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.hp.broadcasttest.MY_BROADCAST");
                //发送标准广播
                sendBroadcast(intent);
                //发送有序广播
                sendOrderedBroadcast(intent, null);
//                Intent intent = new Intent("com.example.hp.broaacasttest.LOCAL_BROADCAST");
//                //发送本地广播
//                mLocalBroadcastManager.sendBroadcast(intent);
            }
        });
//        mIntentFilter = new IntentFilter();
//        //添加广播想要监听的类型
//        mIntentFilter.addAction("com.example.hp.broaacasttest.LOCAL_BROADCAST");
//        mLocalReceiver = new LocalReceiver();
//        //动态注册本地广播监听器
//        mLocalBroadcastManager.registerReceiver(mLocalReceiver, mIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册广播接收器
        unregisterReceiver(mNetworkChangeReceiver);
//        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
    }
    /**
    * 定义一个类继承Broadcast，在onReceive中处理逻辑,广播1
    **/
    private class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //系统服务类，专门管理网络连接的，需要声明权限
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            //判断是否有网
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context,"network is add",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,"network is un",Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }
}
