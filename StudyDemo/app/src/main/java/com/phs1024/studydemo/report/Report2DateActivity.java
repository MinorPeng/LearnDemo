package com.phs1024.studydemo.report;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.base.BaseReport2Activity;

import java.util.Calendar;

/**
 * @author PHS1024
 * @date 2019/10/12 11:15:00
 */
public class Report2DateActivity extends BaseReport2Activity {

    private TextView mTvDate, mTvTime;
    private TimePickerDialog mTimePickerDialog;
    private DatePickerDialog mDatePickerDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report2_date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        Button btnDate = findViewById(R.id.btn_report2_date);
        Button btnTime = findViewById(R.id.btn_report2_time);
        mTvDate = findViewById(R.id.tv_report2_date_date);
        mTvTime = findViewById(R.id.tv_report2_date_time);

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        mTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mTvTime.setText(hourOfDay + ":" + minute);
                setResult(RESULT_OK, new Intent().putExtra("date", mTvTime.getText().toString()));
            }
        }, hour, minute, false);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mTvDate.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, year, month, dayOfMonth);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerDialog.show();
            }
        });

    }
}
