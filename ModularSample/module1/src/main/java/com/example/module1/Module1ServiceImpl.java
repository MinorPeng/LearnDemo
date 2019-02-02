package com.example.module1;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.router.Module1Service;
import com.example.router.RouterConstants;

/**
 * @author 14512 on 2018/4/19
 */
@Route(path = RouterConstants.MODULE1_SERVICE_IMPL)
public class Module1ServiceImpl implements Module1Service {

    @Override
    public boolean share() {
        Log.d("wxl","share");
        return true;
    }

    @Override
    public void init(Context context) {
        Log.d("wxl","init="+context);
    }
}
