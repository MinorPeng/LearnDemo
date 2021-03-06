package com.example.module1.debug;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module1.BuildConfig;

/**
 * @author 14512 on 2018/4/19
 */
public class Module1Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.printStackTrace(); // 打印日志的时候打印线程堆栈
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

    }
}
