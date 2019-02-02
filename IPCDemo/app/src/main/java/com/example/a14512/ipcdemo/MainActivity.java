package com.example.a14512.ipcdemo;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.a14512.ipcdemo.aidl.Book;
import com.example.a14512.ipcdemo.aidl.BookManagerService;

import java.util.List;

/**
 * @author 14512
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemoteBookManager;
    private IOnNewBookListener mListener = new IOnNewBookListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    try {
                        Log.d(TAG, mRemoteBookManager.getBookList().size() + "receive new book" + msg.obj);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteBookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = mRemoteBookManager.getBookList();
                Log.d(TAG, bookList.getClass().getCanonicalName());
                Log.d(TAG, bookList.size() + "");
                mRemoteBookManager.addBook(new Book(3, "new book"));
                List<Book> newList = mRemoteBookManager.getBookList();
                Log.d(TAG, newList.size() + "");
                mRemoteBookManager.registerListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, BookManagerService.class);
//        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Button btnMessenger = findViewById(R.id.btn_messenger_activity);
        btnMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MessengerActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                Log.d(TAG, "unregister listener");
                mRemoteBookManager.unregisterListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
