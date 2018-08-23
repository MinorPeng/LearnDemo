package com.example.a14512.jetpackpagingdemo.pagingdemo

/**
 * @author 14512 on 2018/8/22
 * 给不同的Repository实现共享的通用接口
 */
interface IStuRepository {
    fun getStudentList(pageSize: Int): Listing<StuBean?>
}