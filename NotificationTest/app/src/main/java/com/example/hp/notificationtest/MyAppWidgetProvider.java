package com.example.hp.notificationtest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 14512 on 2018/10/9
 */
public class MyAppWidgetProvider extends AppWidgetProvider {
    public static final String TAG = "MyAppWidgetProvider";
    public static final String CLICK_ACTION = "com.example.hp.notificationtest.action.CLICK";

    private static final int CORE_POOL_SIZE = 1;
    private static final int MAX_NUM = 1;
    private static final int KEEP_ALIVE = 1;
    private static final BlockingDeque<Runnable> RUNNABLES = new LinkedBlockingDeque<>(16);
    private static final ThreadFactory FACTORY = new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r);
        }
    };
    private static final Executor EXECUTOR_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_NUM,
            KEEP_ALIVE, TimeUnit.SECONDS, RUNNABLES, FACTORY);

    public MyAppWidgetProvider() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive : action " + intent.getAction());
        //判断自己Action，然后逻辑
        if (Objects.equals(intent.getAction(), CLICK_ACTION)) {
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
            EXECUTOR_POOL.execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    for (int i = 0; i < 37; i++) {
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                                R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.imgWidget,
                                rotateBitmap(context, srcBitmap, degree));
                        Intent intentClick = new Intent();
                        intentClick.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                                intentClick, 0);
                        remoteViews.setOnClickPendingIntent(R.id.imgWidget, pendingIntent);
                        manager.updateAppWidget(new ComponentName(context,
                                MyAppWidgetProvider.class), remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            });
        }
    }

    /**
     * 每次小部件更新都会调用此方法
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate");
        final int counter = appWidgetIds.length;
        Log.d(TAG, "counter = " + counter);
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    /**
     * 小部件更新
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "appWidgetId = " + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.imgWidget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        return Bitmap.createBitmap(srcBitmap, 0, 0,
                srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
    }


}
