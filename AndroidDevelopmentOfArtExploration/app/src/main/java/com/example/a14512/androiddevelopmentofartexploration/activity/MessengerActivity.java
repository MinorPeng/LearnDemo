package com.example.a14512.androiddevelopmentofartexploration.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.a14512.androiddevelopmentofartexploration.R;
import com.example.a14512.androiddevelopmentofartexploration.service.MessengerService;

/**
 * Created by 14512 on 2017/8/8.
 */


public class MessengerActivity extends Activity {

    private static final String TAG = " MessengerActivity";

    private Messenger mService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message message = Message.obtain(null, 2);
            Bundle data = new Bundle();
            data.putString("msg", "hello, this is client");  //由于进程不同，查看时注意查看的方法
            message.setData(data);
            message.replyTo = messenger;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger messenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.i(TAG, "receive :" + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }
}