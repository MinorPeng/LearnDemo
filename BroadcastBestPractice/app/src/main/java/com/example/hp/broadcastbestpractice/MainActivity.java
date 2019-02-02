package com.example.hp.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    private EditText accountEditext,  passwordEditext;
    private Button loginButton;
    private CheckBox rememberPass;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        accountEditext = (EditText) findViewById(R.id.account);
        passwordEditext = (EditText) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.remember_password);
        loginButton = (Button) findViewById(R.id.login);
        final boolean isRemember = prefs.getBoolean("remember_password", false);
        if (isRemember) {
            //将帐号和密码都设置到文本框中
            String account = prefs.getString("account", "");
            String password = prefs.getString("password", "");
            accountEditext.setText(account);
            passwordEditext.setText(password);
            rememberPass.setChecked(true);
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEditext.getText().toString();
                String password = passwordEditext.getText().toString();
                if (account.equals("admin") && password.equals("123456")) {
                    editor = prefs.edit();
                    if (rememberPass.isChecked()) {  //检查复选框是否被选中
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password",password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
