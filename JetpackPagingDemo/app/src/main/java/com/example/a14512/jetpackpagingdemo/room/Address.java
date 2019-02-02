package com.example.a14512.jetpackpagingdemo.room;

import android.arch.persistence.room.ColumnInfo;

/**
 * @author 14512 on 2018/9/2
 */
public class Address {
    public String street;
    public String state;
    public String city;

    @ColumnInfo(name = "post_code")
    public int postCode;
}
