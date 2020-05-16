package com.minorpeng.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val translateAnimation = TranslateAnimation(0f, 500f, 0f, 200f)
        // translateAnimation.duration = 3000
        val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.tranlate_test)
        tv_hello.startAnimation(translateAnimation)

        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_test)
        // tv_hello.startAnimation(scaleAnimation)

    }
}
