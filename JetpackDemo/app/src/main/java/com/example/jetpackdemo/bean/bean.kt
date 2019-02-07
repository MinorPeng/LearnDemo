package com.example.jetpackdemo.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author phs on 19-2-6
 */
data class DateGank(var category: ArrayList<String>, var error: Boolean, var results: DateResult) {
    data class DateResult(@SerializedName("Android") var android: ArrayList<DateGankData>? = null,
                          @SerializedName("App") var app: ArrayList<DateGankData>? = null,
                          @SerializedName("iOS") var ios: ArrayList<DateGankData>? = null,
                          @SerializedName("前端") var web: ArrayList<DateGankData>? = null,
                          @SerializedName("瞎推荐") var recommend: ArrayList<DateGankData>? = null,
                          @SerializedName("休息视频") var restVideo: ArrayList<DateGankData>? = null)

    data class DateGankData(@SerializedName("_id") var id: String,
                            var createdAt: String,
                            var desc: String,
                            var images: ArrayList<String>?,
                            var publishedAt: String,
                            var source: String,
                            var type: String,
                            var url: String,
                            var used: Boolean,
                            var who: String) : Serializable

}