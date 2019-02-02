package com.example.a14512.audiovediodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a14512.audiovediodemo.camera2.Camera2DemoActivity;
import com.example.a14512.audiovediodemo.wechat.WeChatVideoActivity;

/**
 * @author 14512
 */
public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        Button btnRecord = findViewById(R.id.btn_record_video);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecordVideoActivity.class));
            }
        });
        Button btnMedia = findViewById(R.id.btn_media_player);
        btnMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MediaPlayerActivity.class));
            }
        });
        Button btnExo = findViewById(R.id.btn_exo_player);
        btnExo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExoPlayerActivity.class));
            }
        });

        Button btnWeChat = findViewById(R.id.btn_we_chat);
        btnWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WeChatVideoActivity.class));
            }
        });

        Button btnCamera2 = findViewById(R.id.btn_camera2);
        btnCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Camera2DemoActivity.class));
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
