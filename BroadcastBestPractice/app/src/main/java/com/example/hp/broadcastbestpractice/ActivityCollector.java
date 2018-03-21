package com.example.hp.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/2/23.
 */
//创建一个活动管理器，用于管理所有活动
public class ActivityCollector {
    public static List<Activity> sActivityList = new ArrayList<>();
    //添加活动
    public static void addActivity(Activity activity) {
        sActivityList.add(activity);
    }
    //移除活动
    public static void removeactivity(Activity activity) {
        sActivityList.remove(activity);
    }
    //关闭管理器中的所有活动
    public static void finishAll() {
        for (Activity activity : sActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
