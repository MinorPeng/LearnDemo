package com.example.a14512.scollertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * @author 14512
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final LinearLayout layout = findViewById(R.id.main_linear_layout);
        Button btnScrollTo = findViewById(R.id.btn_scroll_to);
        Button btnScrollBy = findViewById(R.id.btn_scroll_by);

        btnScrollTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //位置
//                layout.scrollTo(-60, -100);
            }
        });

        btnScrollBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                layout.scrollBy(-60, -100);
            }
        });
        LayoutInflater layoutInflater = LayoutInflater.from(this);
    }
}
