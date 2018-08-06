package com.example.a14512.jetpackpagingdemo

import com.example.a14512.jetpackpagingdemo.bean.KituItem

/**
 * @author 14512 on 2018/7/7
 */
interface KituRepository {
    fun getStudentList(pageSize: Int): Listing<KituItem?>
}