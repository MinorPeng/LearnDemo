package com.example.hp.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:
                Intent intent = new Intent(this, NotificationActivity.class);
                //获取PendingIntent实例
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationManager manager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("This is content title")  //设置标题
                        .setContentText("This is content text")  //设置内容
                        .setWhen(System.currentTimeMillis())  //设置时间为系统时间
                        .setSmallIcon(R.mipmap.ic_launcher)  //小图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))  //大图标
                        //延迟执行的Intent，Intent是立即执行，PendingIntent是延迟性的
                        .setContentIntent(pi)
                        .setAutoCancel(true)  //当点击通知后，就取消显示通知
                        //在通知发出的时候播放一段音频
                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                        //手机振动，振动一秒，静止一秒，一次类推，次数与数组的长度有关
                        .setVibrate(new long[] {0, 1000, 1000, 1000})
                        //LED灯的设置，第一个参数为颜色，第二个为亮起的时长，第三个为熄灭的时长
                        .setLights(Color.BLUE, 1000, 1000)
                        //全部系统默认的设置
//                        .setDefaults(NotificationCompat.DEFAULT_ALL);
                        //显示长文字
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn" +
                                "how to build notification, send and sync data, and" +
                                "use voice action. Get the official Android IDE and " +
                                "developer tools to build apps for Android"))
                        //设置通知的重要程度,5个程度
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1, notification);  //通知显示出来
                break;
            default:
                break;
        }
    }
}
