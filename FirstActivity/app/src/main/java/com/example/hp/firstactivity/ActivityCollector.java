package com.example.hp.firstactivity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/1/14.
 */
//活动管理器

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //销毁活动
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());  //杀掉当前进程的代码
            }
        }
    }
}
