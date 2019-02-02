package com.example.hp.secondapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_call = (Button) findViewById(R.id.button_call);
        Button button_contacts = (Button) findViewById(R.id.button_contacts);

        button_call.setOnClickListener(this);
        button_contacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_call:
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("tel:"));
                startActivity(intent1);
                break;
            case R.id.button_contacts:
                Intent intent2 = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent2);
        }
    }
}
