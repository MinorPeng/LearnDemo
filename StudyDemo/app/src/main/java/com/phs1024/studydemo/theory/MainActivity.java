package com.phs1024.studydemo.theory;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phs1024.studydemo.R;

/**
 * @author PHS1024
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHello = findViewById(R.id.tv_hello);
        ImageView ivMain = findViewById(R.id.iv_main);
        ivMain.setImageResource(R.drawable.ic_launcher_background);


    }
}
