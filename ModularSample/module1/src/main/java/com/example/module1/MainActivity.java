package com.example.module1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.base.BaseActivity;
import com.example.common.model.UserModel;
import com.example.router.RouterConstants;
import com.example.router.RouterUtils;

/**
 * @author 14512
 */
@Route(path = RouterConstants.MODULE1_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_activity_main);

        initToolBar(getString(R.string.module1));
        TextView module1Text = (TextView) findViewById(R.id.module1_text);
        Button btnModule = (Button) findViewById(R.id.module1_button);

        if (this.getIntent() != null) {
            UserModel userModel = (UserModel) this.getIntent().getSerializableExtra("obj");
            if (userModel != null) {
                module1Text.setText("带参数："+userModel.getName() + "，" + userModel.getMessage());
            }
        }

        btnModule.setOnClickListener(view -> {
            int id = view.getId();
            if (id == R.id.module1_button) {
                RouterUtils.navigation(RouterConstants.MODULE2_MAIN_ACTIVITY);
            }
        });
    }
}
