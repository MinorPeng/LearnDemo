package com.example.a14512.weatherkotlin.view

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import com.bumptech.glide.Glide
import com.example.a14512.weatherkotlin.R
import com.example.a14512.weatherkotlin.bean.Weather
import com.example.a14512.weatherkotlin.config.WEATHER
import com.example.a14512.weatherkotlin.okhttp3.HttpUtil
import com.example.a14512.weatherkotlin.okhttp3.Utility
import com.example.a14512.weatherkotlin.utils.ToastUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class WeatherActivity : AppCompatActivity() {

    val drawerLayout: DrawerLayout? = null
    val swipeRefresh: SwipeRefreshLayout? = null
    private val scrollView: ScrollView? = null
    private var bgImg: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        initView()
    }

    private fun showWeatherInfo(weather: Weather?) {

    }

    private fun initView() {

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val weatherString = prefs.getString(WEATHER, null)
        val weatherId: String?
        if (weatherString != null) {
            val weather = Utility.handleWeatherResponse(weatherString)
            weatherId = weather?.basic?.weatherId
            showWeatherInfo(weather)
        } else {
            weatherId = intent.getStringExtra("weather_id")
            scrollView!!.visibility = View.VISIBLE
            requestWeather(weatherId)
        }
        swipeRefresh!!.setOnRefreshListener { requestWeather(weatherId) }
        val bingPic = prefs.getString("bing_pic", null)
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bgImg)
        } else {
            loadBingPic()
        }
    }

    fun requestWeather(id: String?) {
        val weatherUrl = "https://geekori.com/api/weather?id=$id"
        HttpUtil.sendOkHttpRequest(weatherUrl, object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val responseText = response!!.body()!!.string()
                val weather = Utility.handleWeatherResponse(responseText)
                runOnUiThread {
                    if (weather != null && "ok" == weather.status) {
                        val editor = PreferenceManager.getDefaultSharedPreferences(this@WeatherActivity).edit()
                        editor.putString(WEATHER, responseText)
                        editor.apply()
                        showWeatherInfo(weather)
                    } else {
                        ToastUtil.show(this@WeatherActivity, "获取天气信息失败!")
                    }
                    swipeRefresh?.isRefreshing = false
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                e!!.printStackTrace()
                runOnUiThread {
                    ToastUtil.show(this@WeatherActivity, "获取天气信息失败!")
                    swipeRefresh?.isRefreshing = false
                }
            }

        })
    }

    private fun loadBingPic() {
        val requestBingPic = "https://geekori.com/api/backgroud/pic"
        HttpUtil.sendOkHttpRequest(requestBingPic, object : okhttp3.Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                e!!.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response?) {
                val bigPic = response!!.body()!!.string()
                val editor = PreferenceManager.getDefaultSharedPreferences(this@WeatherActivity).edit()
                editor.putString("bing_pic", bigPic)
                editor.apply()
                runOnUiThread {
                    Glide.with(this@WeatherActivity).load(bigPic).into(bgImg)
                }
            }

        })
    }
}
