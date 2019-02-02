package com.example.a14512.fromsmalltospecialist.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a14512.fromsmalltospecialist.R;
import com.example.a14512.fromsmalltospecialist.nine.MathUtils;

/**
 * @author 14512 on 2018/4/10
 */

public class CalculatorActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
    }

    private void initView() {
        Button btnCalculate = findViewById(R.id.btnCalculate);
        final EditText edt1 = findViewById(R.id.editText);
        final EditText edt2 = findViewById(R.id.editText2);
        final TextView tvResult = findViewById(R.id.tvResult);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int param1 = Integer.valueOf(edt1.getText().toString().trim());
                int param2 = Integer.valueOf(edt2.getText().toString().trim());
                int result = MathUtils.add(param1, param2);
                tvResult.setText(String.valueOf(result));
            }
        });
    }
}
