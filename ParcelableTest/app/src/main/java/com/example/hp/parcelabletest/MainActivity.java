package com.example.hp.parcelabletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hp.parcelabletest.Activity.SecondActivity;
import com.example.hp.parcelabletest.Data.Person;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.setName("Tom");
                person.setAge(18);
                person.setAddress("重庆邮电");
                person.setBirthday("1997.05.01");
                person.setReportCard(84.50);
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("person_data", person);
                startActivity(intent);
            }
        });
    }
}
