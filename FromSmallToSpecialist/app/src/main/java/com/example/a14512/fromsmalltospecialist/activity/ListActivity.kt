package com.example.a14512.fromsmalltospecialist.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.a14512.fromsmalltospecialist.R
import com.example.a14512.fromsmalltospecialist.nine.MathUtils
import kotlinx.android.synthetic.main.activity_list.*

/**
 * @author 14512 on 2018/4/3
 */
class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initView()
    }

    private fun initView() {
        btnCalculate.setOnClickListener({
            var param1: Int = editText.text.toString().trim().toInt()
            var param2 = editText2.text.toString().trim().toInt()
            var result = MathUtils.add(param1, param2)
            tvResult.text = result.toString()
        })
    }
}