package com.example.a14512.weatherkotlin.okhttp3

import android.text.TextUtils
import com.example.a14512.weatherkotlin.bean.City
import com.example.a14512.weatherkotlin.bean.County
import com.example.a14512.weatherkotlin.bean.Province
import com.example.a14512.weatherkotlin.bean.Weather
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @author 14512 on 2018/3/18
 */
object Utility {
    //解析和处理服务器返回的省级数据
    fun handleProvinceResponse(response: String): List<Province> {
        var provinces = mutableListOf<Province>()
        if (!TextUtils.isEmpty(response)) {
            try {
                //将JSON数组 转化为Kotlin数组形式
                val allProvinces = JSONArray(response)
                //对数组循环，创建一个Province对象
                for (i in 0 until allProvinces.length()) {
                    val provinceObject = allProvinces.getJSONObject(i)
                    val province = Province(provinceName = provinceObject.getString("name"),
                            provinceCode = provinceObject.getString("id"))
                    provinces.add(provinces.size, province)
                }
            } catch (e : JSONException) {
                e.printStackTrace()
            }
        }
        return provinces
    }

    //市级数据
    fun handleCityResponse(response: String, provinceCode: String): List<City> {
        var cities = mutableListOf<City>()
        if (!TextUtils.isEmpty(response)) {
            try {
                val allCities = JSONArray(response)
                for (i in 0 until allCities.length()) {
                    val cityObject = allCities.getJSONObject(i)
                    val city = City(cityName = cityObject.getString("name"),
                            cityCode = cityObject.getString("id"), provinceCode = provinceCode)
                    cities.add(city)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return cities
    }

    //县级数据
    fun handleCountyResponse(response: String, cityCode: String): List<County> {
        var counties = mutableListOf<County>()
        if (!TextUtils.isEmpty(response)) {
            try {
                val allCounties = JSONArray(response)
                for (i in 0 until allCounties.length()) {
                    val countyObject = allCounties.getJSONObject(i)
                    val county = County(countyName = countyObject.getString("name"),
                            countyCode = countyObject.getString("id"),
                            cityCode = cityCode)
                    counties.add(county)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return counties
    }

    //返回weather实体类
    fun handleWeatherResponse(response: String): Weather? {
        try {
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("HeWeather")
            val weatherContent = jsonArray.getJSONObject(0).toString()
            return Gson().fromJson(weatherContent, Weather::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}