package com.example.a14512.founddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.a14512.founddemo.R;

/**
 * @author 14512 on 2018/1/25
 */

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        float nowVersionCode = getVersionCode(WelcomeActivity.this);
        Log.e(TAG, "now:" + nowVersionCode);
        SharedPreferences sp = getSharedPreferences("welcomeInfo", MODE_PRIVATE);
        float spVersionCode = sp.getFloat("spVersionCode", 0);
        Log.e(TAG, "sp:" + spVersionCode);
        if (nowVersionCode > spVersionCode) {
            setContentView(R.layout.activity_welcome);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("spVersionCode", nowVersionCode);
            editor.apply();

            startActivityForResult(new Intent(this, LoginActivity.class), 999);
        } else {
            //非首次启动
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 999) {
                Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private float getVersionCode(Context context) {
        float versionCode = 0;
        try {
            versionCode = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
