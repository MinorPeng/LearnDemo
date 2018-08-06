package com.example.a14512.customviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a14512.customviewdemo.data.PieData;

import java.util.ArrayList;

/**
 * @author 14512
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PieView pieView = findViewById(R.id.pieView);
        ArrayList<PieData> pieDatas = new ArrayList<>();
        PieData pieData = new PieData("phs", 10);
        PieData pieData1 = new PieData("phs1", 20);
        PieData pieData2 = new PieData("phs2", 40);
        PieData pieData3 = new PieData("phs3", 30);
        pieDatas.add(pieData);
        pieDatas.add(pieData1);
        pieDatas.add(pieData2);
        pieDatas.add(pieData3);
//        pieView.setData(pieDatas);

//        final BezierView2 bezierView2 = findViewById(R.id.bezierView2);
        Button btn1 = findViewById(R.id.btnControl1);
        Button btn2 = findViewById(R.id.btnControl2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bezierView2.setMode(true);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bezierView2.setMode(false);
            }
        });
    }
}
