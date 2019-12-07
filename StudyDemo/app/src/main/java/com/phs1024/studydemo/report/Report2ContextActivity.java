package com.phs1024.studydemo.report;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.base.BaseReport2Activity;

/**
 * @author PHS1024
 * @date 2019/10/12 11:17:34
 */
public class Report2ContextActivity extends BaseReport2Activity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report2_context;
    }

    @Override
    protected void initView() {
        LinearLayout layout = findViewById(R.id.linear_layout_report2_context);
        registerForContextMenu(layout);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.report2_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_date:
                startActivity(new Intent(this, Report2DateActivity.class));
                return true;
            case R.id.menu_dialog:
                startActivity(new Intent(this, Report2DialogActivity.class));
                return true;
            case R.id.menu_context:
                //startActivity(new Intent(this, Report2ContextActivity.class));
                return true;
            case R.id.menu_main_report2:
                startActivity(new Intent(this, Report2Activity.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
