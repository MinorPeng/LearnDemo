package com.phs1024.studydemo.report;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.base.BaseReport2Activity;
import com.phs1024.studydemo.util.TimeUtil;

/**
 * @author PHS1024
 * @date 2019/10/12 10:50:02
 */
public class Report2Activity extends BaseReport2Activity {

    private static final int REQ_CODE = 22;
    private TextView mTvDate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report2_main;
    }

    @Override
    protected void initView() {
        mTvDate = findViewById(R.id.tv_report2_date);
        mTvDate.post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mTvDate.setText(TimeUtil.getCurrentTime() + "\n" + TimeUtil.getCurrentDate());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_date) {
            startActivityForResult(new Intent(this, Report2DateActivity.class), REQ_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQ_CODE && data != null) {
            mTvDate.setText(data.getStringExtra("date"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
