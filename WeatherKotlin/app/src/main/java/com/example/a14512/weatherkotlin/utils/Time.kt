package com.example.a14512.weatherkotlin.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author 14512 on 2018/3/17
 */
object Time {

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getNowYMDHMSTime(): String {

        val mDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return mDateFormat.format(Date())
    }

    /**
     * MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getNowMDHMSTime(): String {
        val mDateFormat = SimpleDateFormat("MM-dd HH:mm:ss")
        return mDateFormat.format(Date())
    }


     /**
     * HH:mm:ss
     * @return
     */
     @SuppressLint("SimpleDateFormat")
     fun getNowHMSTime(): String {
        @SuppressLint("SimpleDateFormat") val mDateFormat = SimpleDateFormat("HH:mm:ss")
        return mDateFormat.format(Date())
    }

    /**
     * MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getNowYMD(): String {

        val mDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return mDateFormat.format(Date())
    }


    /**
     * MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getNowMD(): String {
        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return month.toString() + "月" + day + "日"
    }

    /**
     * yyyy-MM-dd
     * 获取一定格式的时间需求
     * @param date 时间
     * @param format 指定的时间格式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getTime(format: String, date: Date): String {
        val mDateFormat = SimpleDateFormat(format)
        return mDateFormat.format(date)
    }


    @SuppressLint("SimpleDateFormat")
    fun getTime(time: String): String {
        val sdr = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val lcc = java.lang.Long.valueOf(time)!!
        val i = Integer.parseInt(time)
        return sdr.format(Date(i * 1000L))

    }

    /**
     * 时间戳 转换成 指定格式的时间
     * @param format 需要的日期格式 如 "yyyy-MM-dd HH:mm:ss"
     * @param timeStamp 时间戳
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getTime(format: String, timeStamp: Long): String {

        val mDateFormat = SimpleDateFormat(format)
        return mDateFormat.format(timeStamp * 1000)
    }

}