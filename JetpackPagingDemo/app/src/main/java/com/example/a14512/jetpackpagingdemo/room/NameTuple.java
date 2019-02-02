package com.example.a14512.jetpackpagingdemo.room;

import android.arch.persistence.room.ColumnInfo;

/**
 * 关心一个表部分字段的值，这时仅需要查询关心的列即可。
 * @author 14512 on 2018/9/2
 */
public class NameTuple {
    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;
}
