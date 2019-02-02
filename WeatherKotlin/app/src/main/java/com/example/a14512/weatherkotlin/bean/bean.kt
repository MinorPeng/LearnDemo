package com.example.a14512.weatherkotlin.bean

/**
 * @author 14512 on 2018/3/18
 */
data class City(var id: Int = 0, var cityName: String,
                var cityCode: String, var provinceCode: String)

data class Province(var  id: Int = 0, var provinceName: String,
                    var provinceCode: String)

data class County(var id: Int = 0, var countyName: String,
             var countyCode: String, var cityCode: String)


