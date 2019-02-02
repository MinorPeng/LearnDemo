package com.example.a14512.jetpackpagingdemo.room;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * 自定义实现数据转换，方便直接存储到ROOM
 * @author 14512 on 2018/9/2
 */
public class DateConverters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
