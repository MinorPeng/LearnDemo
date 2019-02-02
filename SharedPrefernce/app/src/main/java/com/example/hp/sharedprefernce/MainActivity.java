package com.example.hp.sharedprefernce;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveData = (Button) findViewById(R.id.save_data);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor =
                        getSharedPreferences("data",MODE_PRIVATE).edit();  //指定文件名为Data
                editor.putString("name","Tom");
                editor.putInt("age",18);
                editor.putBoolean("married",false);
                editor.apply();  //提交
            }
        });

        Button restoreData = (Button) findViewById(R.id.restore_data);
        restoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("data",MODE_PRIVATE);
                String name = prefs.getString("name","");
                int age = prefs.getInt("age",0);
                boolean married = prefs.getBoolean("married",false);
                Log.d("12345",":"+name+":"+age+":"+married);
            }
        });

    }
}
