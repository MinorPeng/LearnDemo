package com.example.serialportdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mWeightUtil = object : WeightUtil() {
            override fun onDataReceived(d: Double) {
                Log.e(this@MainActivity.localClassName, "weight   $d")

            }
        }
        mWeightUtil?.open()
    }
}
