package com.example.a14512.audiovediodemo.wechat.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @author 14512 on 2018/9/6
 */
public class Utils {

    private static Utils mInstance = null;
    private Context mContext;

    public static Utils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (Utils.class) {
                if (mInstance == null) {
                    mInstance = new Utils(context);
                }
            }
        }
        return mInstance;
    }

    private Utils(Context context) {
        this.mContext = context;
    }

    public int getWidthPixels() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        Configuration configuration = mContext.getResources().getConfiguration();
        int orientation = configuration.orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            return displayMetrics.heightPixels;
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            return displayMetrics.widthPixels;
        }
        return 0;
    }

    public int getHeightPixels() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        Configuration configuration = mContext.getResources().getConfiguration();
        int orientation = configuration.orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return displayMetrics.widthPixels;
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return displayMetrics.heightPixels;
        }
        return 0;
    }

    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int px2dp(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
