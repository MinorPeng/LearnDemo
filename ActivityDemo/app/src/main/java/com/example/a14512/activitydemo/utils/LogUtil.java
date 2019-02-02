package com.example.a14512.activitydemo.utils;

import android.util.Log;

/**
 * @author 14512 on 2018/7/30
 */
public class LogUtil {

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String msg) {
        e(getClassName(), msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void w(String msg) {
        w(getClassName(), msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void i(String msg) {
        i(getClassName(), msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void d(String msg) {
        d("LogUtil  "+getClassName(), msg);
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void v(String msg) {
        v(getClassName(), msg);
    }

    /**
     * @return 当前的类名(simpleName)
     */
    public static String getClassName() {
        String result;
//        StackTraceElement thisMethodStack = Thread.currentThread().getStackTrace()[2];
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1);

        // TODO: 16/5/10 调试下 内部类问题
        // 剔除匿名内部类名
        int i = result.indexOf("$");
        return i == -1 ? result : result.substring(0, i);
    }
}
