package com.example.a14512.weatherkotlin.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.a14512.weatherkotlin.R
import com.example.a14512.weatherkotlin.bean.City
import com.example.a14512.weatherkotlin.bean.County
import com.example.a14512.weatherkotlin.bean.Province
import com.example.a14512.weatherkotlin.okhttp3.DataSupport
import com.example.a14512.weatherkotlin.utils.PLog
import kotlinx.android.synthetic.main.fragment_choose_area.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author 14512
 */
class ChooseAreaFragment : Fragment() {

    private var progressDialog: ProgressDialog? = null
    private var title: TextView? = null
    private var leftImg: ImageView? = null
    private var list: ListView? = null

    private var adapter: ArrayAdapter<String>? = null
    private var handler = MyHandler()

    private val dataList = ArrayList<String>()
    private var provinceList: ArrayList<Province>? = null
    private var cityList: ArrayList<City>? = null
    private var countyList: ArrayList<County>? = null

    private var selectedProvince: Province? = null
    private var selectedCity: City? = null
    private var currentLevel: Int = 0

    companion object {
        const val LEVEL_PROVINCE = 0
        const val LEVEL_CITY = 1
        const val LEVEL_COUNTY = 2
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_choose_area, container, false)
        PLog.e("frag")
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        title = tvToolbarTitle
        leftImg = imgToolbarLeft
        list = chooseListView
        title!!.text = "145123"
        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, dataList)
        adapter!!.notifyDataSetChanged()
        list!!.adapter = adapter!!
        PLog.e(currentLevel.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        queryProvinces()
        //设置listView的 Item的点击事件
        list!!.onItemClickListener = AdapterView.OnItemClickListener {
            parent, view, position, id ->
            if (currentLevel == LEVEL_PROVINCE) {
                selectedProvince = provinceList!![position]
                queryCities()
            } else if (currentLevel == LEVEL_CITY) {
                selectedCity = cityList!![position]
                queryCounties()
            } else if (currentLevel == LEVEL_COUNTY) {
                val countyName = countyList!![position].countyName
                if (activity is MainActivity) {
                    val intent = Intent(activity, WeatherActivity::class.java)
                    intent.putExtra("name", countyName)
                    startActivity(intent)
                    activity.finish()
                } else if (activity is WeatherActivity) {
                    val activity = activity as WeatherActivity
                    activity.drawerLayout?.closeDrawers()
                    activity.swipeRefresh?.isRefreshing = true
                    activity.requestWeather(countyName)
                }
            }
        }

        leftImg!!.setOnClickListener {
            if (currentLevel == LEVEL_COUNTY) {
                queryCities()
            } else if (currentLevel == LEVEL_CITY) {
                queryProvinces()
            }
        }
    }

    class MyHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            var activity = msg?.obj as ChooseAreaFragment
            when (msg.arg1) {
                //在listV中显示省列表
                ChooseAreaFragment.LEVEL_PROVINCE -> {
                    if (activity.provinceList!!.size > 0) {
                        activity.dataList.clear()
                        for (province in activity.provinceList!!) {
                            activity.dataList.add(province.provinceName)
                        }
                        activity.adapter!!.notifyDataSetChanged()
                        activity.list!!.setSelection(0)
                        activity.currentLevel = LEVEL_PROVINCE
                    }
                }

                ChooseAreaFragment.LEVEL_CITY -> {
                    if (activity.cityList!!.size > 0) {
                        activity.dataList.clear()
                        for (city in activity.cityList!!) {
                            activity.dataList.add(city.cityName)
                        }
                        activity.adapter!!.notifyDataSetChanged()
                        activity.list!!.setSelection(0)
                        activity.currentLevel = LEVEL_CITY
                    }
                }

                ChooseAreaFragment.LEVEL_COUNTY -> {
                    if (activity.countyList!!.size > 0) {
                        activity.dataList.clear()
                        for (county in activity.countyList!!) {
                            activity.dataList.add(county.countyName)
                        }
                        activity.adapter!!.notifyDataSetChanged()
                        activity.list!!.setSelection(0)
                        activity.currentLevel = LEVEL_COUNTY
                    }
                }
            }
        }
    }

    private fun queryProvinces() {
        title!!.text = "中国"
        leftImg!!.visibility = View.GONE
        DataSupport.getProvince {
            provinceList = it
            var msg = Message()
            msg.obj = this
            msg.arg1 = LEVEL_PROVINCE
            handler.sendMessage(msg)
        }
    }

    private fun queryCounties() {
        title!!.text = selectedCity!!.cityName
        leftImg!!.visibility = View.VISIBLE
        DataSupport.getCounties(selectedProvince!!.provinceCode, selectedCity!!.cityCode) {
            countyList = it
            var msg = Message()
            msg.obj = this
            msg.arg1 = LEVEL_COUNTY
            handler.sendMessage(msg)
        }
    }

    private fun queryCities() {
        title!!.text = selectedProvince!!.provinceName
        leftImg!!.visibility = View.VISIBLE
        DataSupport.getCities(selectedProvince!!.provinceCode) {
            cityList = it
            var msg = Message()
            msg.obj = this
            msg.arg1 = LEVEL_CITY
            handler.sendMessage(msg)
        }
    }
}

