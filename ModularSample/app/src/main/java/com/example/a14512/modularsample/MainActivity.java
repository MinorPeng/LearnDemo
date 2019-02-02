package com.example.a14512.modularsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.base.BaseActivity;
import com.example.common.model.UserModel;
import com.example.router.Module1Service;
import com.example.router.RouterConstants;
import com.example.router.RouterUtils;



/**
 * @author 14512
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);
        initToolBarAsHome(getString(R.string.app_name));

        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        Button btn4 = (Button) findViewById(R.id.button4);

        btn1.setOnClickListener(v -> RouterUtils.navigation(RouterConstants.MODULE1_MAIN_ACTIVITY));
        btn2.setOnClickListener(v -> {
            UserModel userModel = new UserModel();
            userModel.setName("WuXiaolong");
            userModel.setMessage("个人博客：http：//wuxiaolong.me");
            Bundle bundle = new Bundle();
            bundle.putSerializable("obj", userModel);
            //有数据传递的路由启动
            ARouter.getInstance()
                    .build(RouterConstants.MODULE1_MAIN_ACTIVITY)
                    .with(bundle)
                    .navigation();
        });
        btn3.setOnClickListener(v ->  showFragment());
        btn4.setOnClickListener(v -> {
            Module1Service module1Service = (Module1Service) RouterUtils.navigation(RouterConstants.MODULE1_SERVICE_IMPL);
            if (module1Service != null) {
                toastShow("结果：" + module1Service.share());
            }
        });
    }

    private void showFragment() {
        // 获取Fragment
        Fragment fragment = (Fragment) RouterUtils.navigation(RouterConstants.MODULE1_MODULE1_FRAGMENT);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.contentLayout, fragment).commit();
        }
    }
}
