package com.phs1024.studydemo.report.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.Report2Activity;
import com.phs1024.studydemo.report.Report2ContextActivity;
import com.phs1024.studydemo.report.Report2DateActivity;
import com.phs1024.studydemo.report.Report2DialogActivity;

/**
 * @author PHS1024
 * @date 2019/10/12 11:06:16
 */
public abstract class BaseReport2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.report2_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_date:
                startActivity(new Intent(this, Report2DateActivity.class));
                return true;
            case R.id.menu_dialog:
                startActivity(new Intent(this, Report2DialogActivity.class));
                return true;
            case R.id.menu_context:
                startActivity(new Intent(this, Report2ContextActivity.class));
                return true;
            case R.id.menu_main_report2:
                startActivity(new Intent(this, Report2Activity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * get content layout id
     * @return int, layout id
     */
    protected abstract int getLayoutId();

    /**
     * init
     */
    protected abstract void initView();
}
