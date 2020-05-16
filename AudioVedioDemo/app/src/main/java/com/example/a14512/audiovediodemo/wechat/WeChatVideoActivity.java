package com.example.a14512.audiovediodemo.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a14512.audiovediodemo.R;

/**
 * @author 14512 on 2018/9/6
 */
public class WeChatVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_video);

        Button btnVideo = findViewById(R.id.btn_video);
        Button btnAudio = findViewById(R.id.btn_audio);
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeChatVideoActivity.this, VideoActivity.class));
            }
        });
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeChatVideoActivity.this, AudioActivity.class));
            }
        });
    }
}
