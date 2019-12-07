package com.phs1024.studydemo.theory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.phs1024.studydemo.R;

/**
 * @author PHS1024
 * @date 2019/9/24 20:08:47
 */
public class Work3Activity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox mCbMusic1, mCbMusic2, mCbMusic3;
    private TextView mTvResult;
    private RadioButton mRbMale, mRbFemale;
    private EditText mEtName;
    private TextView mTvSexResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work3);
        initView();
    }

    private void initView() {
        mCbMusic1 = findViewById(R.id.cb_music1);
        mCbMusic2 = findViewById(R.id.cb_music2);
        mCbMusic3 = findViewById(R.id.cb_music3);
        Button btnChose = findViewById(R.id.btn_chose);
        mTvResult = findViewById(R.id.tv_chose_music);

        mRbMale = findViewById(R.id.rb_male);
        mRbFemale = findViewById(R.id.rb_female);
        mEtName = findViewById(R.id.et_input_name);
        Button btnSubmit = findViewById(R.id.btn_submit);
        mTvSexResult = findViewById(R.id.tv_chose_sex);

        btnChose.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chose:
                btnSubmitMusic();
                break;
            case R.id.btn_submit:
                btnSubmitSex();
                break;
            default:
                break;
        }
    }

    private void btnSubmitSex() {
        StringBuilder sb = new StringBuilder();
        sb.append("您输入的信息为：\n");
        sb.append("姓名 ");
        sb.append(mEtName.getText());
        sb.append(" 性别 ");
        if (mRbMale.isChecked()) {
            sb.append(mRbMale.getText());
        } else if (mRbFemale.isChecked()) {
            sb.append(mRbFemale.getText());
        }
        mTvSexResult.setText(sb);
    }

    private void btnSubmitMusic() {
        StringBuilder sb = new StringBuilder();
        sb.append("您选择了：\n");
        if (mCbMusic1.isChecked()) {
            sb.append(mCbMusic1.getText());
            sb.append("\n");
        }
        if (mCbMusic2.isChecked()) {
            sb.append(mCbMusic2.getText());
            sb.append("\n");
        }
        if (mCbMusic3.isChecked()) {
            sb.append(mCbMusic3.getText());
            sb.append("\n");
        }
        mTvResult.setText(sb);
    }
}
