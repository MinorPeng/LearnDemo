package com.phs1024.studydemo.theory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.phs1024.studydemo.R;

/**
 * @author PHS1024
 * @date 2019/9/17 12:27:44
 */
public class Work2Activity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvHello, mTvHint;
    private EditText mEtPwd;

    private boolean isChanged = false;
    private String mRealPwd = "abc123";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work2);
        mTvHello = findViewById(R.id.tv_text);
        Button btnChangeBg = findViewById(R.id.btn_click_change_bg_color);
        btnChangeBg.setOnClickListener(this);

        mTvHint = findViewById(R.id.tv_hint);
        mEtPwd = findViewById(R.id.et_input_pwd);
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_click_change_bg_color:
                changeBg();
                break;
            case R.id.btn_submit:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        String pwd = mEtPwd.getText().toString();
        if (pwd.isEmpty()) {
            mTvHint.setText("密码不能为空");
        } else {
            if (pwd.equals(mRealPwd)) {
                mTvHint.setText("欢迎进去快乐大本营");
            } else {
                mTvHint.setText("非法用户，请离开!");
            }
        }

    }

    private void changeBg() {
        if (isChanged) {
            mTvHello.setBackgroundColor(Color.WHITE);
            mTvHello.setTextColor(Color.BLACK);
            isChanged = false;
        } else {
            mTvHello.setTextColor(Color.WHITE);
            mTvHello.setBackgroundColor(Color.BLUE);
            isChanged = true;
        }
    }
}
