package com.phs1024.studydemo.util;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author PHS1024
 * @date 2019/10/12 11:41:47
 */
public class TimeUtil {

    private TimeUtil() {

    }

    public static String getCurrentDetailedTime() {
        // 格式化时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat();
        // a为am/pm的标记
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        // 获取当前时间
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getCurrentDate() {
        // 格式化时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat();
        // a为am/pm的标记
        sdf.applyPattern("yyyy-MM-dd");
        // 获取当前时间
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getCurrentTime() {
        // 格式化时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat();
        // a为am/pm的标记
        sdf.applyPattern("HH:mm:ss");
        // 获取当前时间
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getFormatTime(@NonNull String format) {
        // 格式化时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat();
        // a为am/pm的标记
        sdf.applyPattern(format);
        // 获取当前时间
        Date date = new Date();
        return sdf.format(date);
    }
}
