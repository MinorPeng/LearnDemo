package com.example.hp.parcelabletest.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hp.parcelabletest.Data.Person;
import com.example.hp.parcelabletest.R;
import com.example.hp.parcelabletest.Util.LogUtil;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = (TextView) findViewById(R.id.person_text);
        Person person = (Person) getIntent().getParcelableExtra("person_data");
        textView.setText(person.getName()+"\n"+person.getAge()+"\n"+person.getAddress()
                +"\n"+person.getBirthday()+"\n"+person.getReportCard());
//        LogUtil.level = NOTHING;
        LogUtil.d("Tag", person.getName());
    }
}
