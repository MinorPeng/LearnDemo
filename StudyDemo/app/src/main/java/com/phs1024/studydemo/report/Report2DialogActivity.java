package com.phs1024.studydemo.report;

import android.content.Intent;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.base.BaseReport2Activity;

/**
 * @author PHS1024
 * @date 2019/10/12 11:16:57
 */
public class Report2DialogActivity extends BaseReport2Activity {

    private AlertDialog mAlertDialog = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report2_dialog;
    }

    @Override
    protected void initView() {
        show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        show();
    }

    private void show() {
        if (mAlertDialog == null) {
            EditText editText = new EditText(this);
            mAlertDialog = new AlertDialog.Builder(this)
                    .setView(editText).create();
        }
        mAlertDialog.show();
    }
}
