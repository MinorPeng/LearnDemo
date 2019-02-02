package com.example.a14512.recyclerviewandlistviewdemo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.recyclerviewandlistviewdemo.R;
import com.example.a14512.recyclerviewandlistviewdemo.data.Contact;

/**
 * @author 14512
 */
public class DetailActivity extends AppCompatActivity {

    private ImageView mImgPortrait;
    private TextView mTvName;
    private TextView mTvContent;

    public static void actionStart(Context context, Contact data) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtra("data", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        getData();
    }

    private void getData() {
        Contact contact = (Contact) getIntent().getBundleExtra("data").getSerializable("data");
        assert contact != null;
        mImgPortrait.setImageResource(contact.getPortraitId());
        mTvName.setText(contact.getName());
        mTvContent.setText(contact.getContent());

    }

    private void initView() {
        mImgPortrait = findViewById(R.id.imgPortraitDetail);
        mTvName = findViewById(R.id.tvNameDetail);
        mTvContent = findViewById(R.id.tvContentDetail);
    }
}
