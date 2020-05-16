package com.hesheng1024.jetpackdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(MainObserver())
        // lifecycle.currentState 当前生命周期状态
        // mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mViewModel = ViewModelProvider(this, MainViewModelFactory(12)).get(MainViewModel::class.java)
        btn_main_plus.setOnClickListener {
            // mViewModel.counter++
            // refresh()
            mViewModel.plusOne()
        }
        // refresh()
        mViewModel.counter.observe(this, Observer { count ->
            tv_main_info.text = count.toString()
        })
        mViewModel.str.observe(this, Observer {
            Log.d(TAG, "str-> $it")
        })
        mViewModel.smapStr.observe(this, Observer {
            Log.d(TAG, "switch map-> $it")
        })
    }

    private fun refresh() {
        tv_main_info.text = mViewModel.counter.toString()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}
