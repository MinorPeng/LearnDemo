package com.example.jetpackdemo.v

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mNavController = Navigation.findNavController(this, R.id.gank_fragment)

    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp()
    }

}
