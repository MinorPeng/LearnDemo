package com.example.hp.secondapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by HP on 2017/1/16.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        Intent intent = getIntent();

        EditText editText = (EditText) findViewById(R.id.edit_1);
        editText.setText(intent.getStringExtra("extra_data"));


    }

}
