package com.example.hp.broaacasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author 14512
 * 静态注册
 * */
public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //静态注册实现开机启动
        Toast.makeText(context,"boot complete",Toast.LENGTH_SHORT).show();
    }
}
