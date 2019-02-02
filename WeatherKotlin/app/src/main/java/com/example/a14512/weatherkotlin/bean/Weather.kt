package com.example.a14512.weatherkotlin.bean

import com.google.gson.annotations.SerializedName

/**
 * @author 14512 on 2018/3/18
 */
data class Weather(var status: String = "", var basic: Basic, var aqi: AQI, var now: Now,
                   var suggestion: Suggestion,
                   @SerializedName("daily_forecast") var dailyList: ArrayList<DailyForecast>,
                   @SerializedName("hourly_forecast") var hourlyList: ArrayList<HourlyForecast>) {

    data class Basic(@SerializedName("city") var cityName: String = "",
                     @SerializedName("cnty") var country: String = "",
                     @SerializedName("id") var weatherId: String = "",
                     @SerializedName("lat") var lat: String = "",
                     @SerializedName("lon") var lon: String = "",
                     var update: Update) {
        data class Update(@SerializedName("loc") var updateTime: String= "")
    }

    data class AQI(var aqiCity: AQICity) {
        data class AQICity(var aqi: String = "", var pm25: String = "")
    }

    data class DailyForecast(@SerializedName("astro") var astro: Astro,
                             @SerializedName("cond") var cond: Cond,
                             @SerializedName("date") var date: String = "",
                             @SerializedName("hum") var hum: String = "",
                             @SerializedName("pcpn") var pcpn: String = "",
                             @SerializedName("pop") var pop: String = "",
                             @SerializedName("pres") var pres: String = "",
                             @SerializedName("tmp") var tmp: Tmp,
                             @SerializedName("uv") var uv: String = "",
                             @SerializedName("vis") var vis: String = "",
                             @SerializedName("wind") var wind: Wind) {
        data class Astro(@SerializedName("mr") var mr: String = "",
                         @SerializedName("ms") var ms: String = "",
                         @SerializedName("sr") var sr: String = "",
                         @SerializedName("ss") var ss: String = "")

        data class Cond(@SerializedName("code_d") var d: String = "",
                        @SerializedName("code_n") var n: String = "",
                        @SerializedName("txt_d") var infoD: String = "",
                        @SerializedName("txt_n") var infoN: String = "")

        data class Tmp(@SerializedName("max") var max: String = "",
                       @SerializedName("min") var min: String = "")

        data class Wind(@SerializedName("deg") var deg: String = "",
                        @SerializedName("dir") var dir: String = "",
                        @SerializedName("sc") var sc: String = "",
                        @SerializedName("spd") var spd: String = "")
    }

    data class HourlyForecast(@SerializedName("cond") var cond: Cond,
                              @SerializedName("date") var date: String = "",
                              @SerializedName("hum") var hum: String = "",
                              @SerializedName("pop") var pop: String = "",
                              @SerializedName("pres") var pres: String = "",
                              @SerializedName("tmp") var tmp: String = "",
                              @SerializedName("wind") var wind: Wind) {
        data class Cond(@SerializedName("code") var code: String = "",
                        @SerializedName("txt") var info: String = "")

        data class Wind(@SerializedName("deg") var deg: String = "",
                        @SerializedName("dir") var dir: String = "",
                        @SerializedName("sc") var sc: String = "",
                        @SerializedName("spd") var spd: String = "")
    }

    data class Now(@SerializedName("tmp") var temperature: Int = 0,
                   @SerializedName("fl") var fl: String = "",
                   @SerializedName("hum") var hum: String = "",
                   @SerializedName("pcpn") var pcpn: String = "",
                   @SerializedName("vis") var vis: String = "",
                   @SerializedName("cond") var cond: Cond,
                   @SerializedName("wind") var wind: Wind) {

        data class Cond(@SerializedName("code") var code: String = "",
                        @SerializedName("txt") var info: String = "")

        data class Wind(@SerializedName("deg") var deg: String = "",
                        @SerializedName("dir") var dir: String = "",
                        @SerializedName("sc") var sc: String = "",
                        @SerializedName("spd") var spd: String = "")
    }

    data class Suggestion(@SerializedName("comf") var comfort: Comfort,
                          @SerializedName("cw") var carWash: CarWash,
                          @SerializedName("drsg") var dressSuggestion: DressSuggestion,
                          var flu: Flu,
                          var sport: Sport,
                          @SerializedName("trav") var travle: Travle,
                          var uv: Uv) {
        data class Comfort(@SerializedName("brf") var brf: String = "",
                           @SerializedName("txt") var info: String = "")

        data class CarWash(@SerializedName("brf") var brf: String = "",
                           @SerializedName("txt") var info: String = "")

        data class Sport(@SerializedName("brf") var brf: String = "",
                         @SerializedName("txt") var info: String = "")

        data class DressSuggestion(@SerializedName("brf") var brf: String = "",
                                   @SerializedName("txt") var info: String = "")

        data class Travle(@SerializedName("brf") var brf: String = "",
                          @SerializedName("txt") var info: String = "")

        data class Flu(@SerializedName("brf") var brf: String = "",
                       @SerializedName("txt") var info: String = "")

        data class Uv(@SerializedName("brf") var brf: String = "",
                      @SerializedName("txt") var info: String = "")
    }
}

