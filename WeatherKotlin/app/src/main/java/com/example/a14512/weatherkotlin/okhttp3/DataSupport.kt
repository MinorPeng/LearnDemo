package com.example.a14512.weatherkotlin.okhttp3

import com.example.a14512.weatherkotlin.bean.City
import com.example.a14512.weatherkotlin.bean.County
import com.example.a14512.weatherkotlin.bean.Province
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

/**
 * @author 14512 on 2018/3/18
 */
object DataSupport {
    //从InputStream对象读取数据，并转换为ByteArray
    private fun getBytesByInputStream(content: InputStream): ByteArray {
        var bytes: ByteArray? = null
        val bis = BufferedInputStream(content)
        val baos = ByteArrayOutputStream()
        val bos = BufferedOutputStream(baos)
        val buffer = ByteArray(1024 * 8)
        var length = 0
        try {
            while (true) {
                length = bis.read(buffer)
                if (length < 0) {
                    break
                }
                bos.write(buffer, 0, length)
            }
            bos.flush()
            bytes = baos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                bis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return bytes!!
    }

    //获取数据，以字符串返回
    private fun getServerContent(urlStr: String): String {
        var url = URL(urlStr)
        var conn = url.openConnection() as HttpURLConnection
        //默认GET请求
//        conn.requestMethod = "GET"
        //默认从服务端读取
//        conn.doInput = true
        //禁用网络请求
        conn.useCaches = false

        val content = conn.inputStream
        //将InputStream转换为byte数组
        var resposeBody = getBytesByInputStream(content)
        //将字节流以utf-8的格式转为字符串
        var str = kotlin.text.String(resposeBody, Charset.forName("utf-8"))
        return str
    }

    fun getProvince(provinces: (ArrayList<Province>)->Unit) {
        Thread {
            var content = getServerContent("https://geekori.com/api/china")
            var provinces = Utility.handleProvinceResponse(content)
            provinces(provinces as ArrayList<Province>)
        }.start()
    }

    fun getCities(provinceCode: String, cities: (ArrayList<City>)->Unit) {
        Thread {
            var content = getServerContent("https://geekori.com/api/china/$provinceCode")
            var cities = Utility.handleCityResponse(content,provinceCode)
            cities(cities as ArrayList<City>)
        }.start()
    }

    fun getCounties(provinceCode: String, cityCode: String, counties: (ArrayList<County>)->Unit) {
        Thread {
            var content = getServerContent("https://geekori.com/api/china/$provinceCode/$cityCode")
            var counties = Utility.handleCountyResponse(content, cityCode)
            counties(counties as ArrayList<County>)
        }.start()
    }
}