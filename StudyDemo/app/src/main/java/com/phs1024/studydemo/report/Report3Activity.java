package com.phs1024.studydemo.report;

import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.phs1024.studydemo.R;
import com.phs1024.studydemo.report.custom.CircleMoveView;

/**
 * @author PHS1024
 * @date 2019/10/14 10:20:40
 */
public class Report3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report3);
        initView();
    }

    private void initView() {
        final View view = findViewById(R.id.view_rect);
        CircleMoveView circleMoveView = findViewById(R.id.circle_view);
        final Rect rect = new Rect();
        circleMoveView.setMoveListener(new CircleMoveView.MoveListener() {
            @Override
            public void position(PointF pointF) {
                view.getGlobalVisibleRect(rect);
                if (rect.contains((int) pointF.x, (int) pointF.y)) {
                        finish();
                }
            }
        });
    }
}
