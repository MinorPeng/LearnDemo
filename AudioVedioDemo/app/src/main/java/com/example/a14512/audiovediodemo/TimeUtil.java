package com.example.a14512.audiovediodemo;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 14512 on 2018/9/4
 */
public class TimeUtil {

    public static String getCurrentTime() {

        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTime(String dateFormat) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(dateFormat);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
