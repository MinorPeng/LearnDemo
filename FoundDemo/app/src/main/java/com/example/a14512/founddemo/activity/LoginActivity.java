package com.example.a14512.founddemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a14512.founddemo.CompleteWatcher;
import com.example.a14512.founddemo.R;


/**
 * @author 14512
 */
public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "LoginActivity";

    private EditText mEdtLoginNum;
    private EditText mEdtLoginPwd;
    private Button mBtnLogin;
    private CheckBox mCheckBox;
    private String mPwd;
    private String mNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        isLogin();
    }

    private void isLogin() {
        mBtnLogin.setOnClickListener(v -> {
            mNum = mEdtLoginNum.getText().toString();
            mPwd = mEdtLoginPwd.getText().toString();
            if (TextUtils.isEmpty(mNum) || TextUtils.isEmpty(mPwd)) {
                Toast.makeText(this, "帐号和密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        Log.e(TAG, ":" + mNum + " " + mPwd);
                    } else {
                        Log.e(TAG, "don't remember");
                    }
                });
                setResult(RESULT_OK);
                finish();
            }
        });
    }


    private void initView() {
        mEdtLoginNum = findViewById(R.id.edt_login_num);
        mEdtLoginPwd = findViewById(R.id.edt_login_pwd);
        mBtnLogin = findViewById(R.id.btn_login);
        mCheckBox = findViewById(R.id.checkBox_login);

        mCheckBox.setEnabled(false);
        IfCompleteWatcher watcher = new IfCompleteWatcher();
        mEdtLoginNum.addTextChangedListener(watcher);
        mEdtLoginPwd.addTextChangedListener(watcher);
    }


    public class IfCompleteWatcher extends CompleteWatcher {

        @Override
        public void ifCompleteWatcher() {
            ifComplete();
        }
    }

    private void ifComplete() {
        if (TextUtils.isEmpty(mEdtLoginNum.getText().toString().trim())
                || TextUtils.isEmpty(mEdtLoginPwd.getText().toString().trim())) {
            mCheckBox.setEnabled(false);
        } else {
            mCheckBox.setEnabled(true);
        }
    }

}
