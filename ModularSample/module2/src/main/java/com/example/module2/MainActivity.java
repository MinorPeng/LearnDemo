package com.example.module2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.base.BaseActivity;
import com.example.common.model.WeatherModel;
import com.example.common.network.ApiCallback;
import com.example.router.RouterConstants;

/**
 * @author 14512
 */
@Route(path = RouterConstants.MODULE2_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {

    private TextView module2Textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_activity_main);
        initToolBar(getString(R.string.module2));

        module2Textview = (TextView) findViewById(R.id.module2_textview);
        Button btnMoudle2 = (Button) findViewById(R.id.module2_button);
        btnMoudle2.setOnClickListener(v -> loadWeatherData());
    }

    //全国+国外主要城市代码http://mobile.weather.com.cn/js/citylist.xml
    private void loadWeatherData() {
        showProgressDialog();
        addSubscription(apiStores().loadWeatherData("101190201"),
                new ApiCallback<WeatherModel>() {
                    @Override
                    public void onSuccess(WeatherModel model) {
                        WeatherModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
                        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                                + getResources().getString(R.string.time) + weatherinfo.getTime();
                        module2Textview.setText(showData);
                    }

                    @Override
                    public void onFailure(String msg) {
                        toastShow(msg);

                    }

                    @Override
                    public void onFinish() {
                        dismissProgressDialog();
                    }
                });
    }
}
