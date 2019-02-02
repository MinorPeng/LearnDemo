package com.example.a14512.jetpackpagingdemo.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author 14512 on 2018/9/2
 */
@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id", childColumns = "user_id"))
public class Book {
    //通过外键引用一个Entity对象，不允许直接引用

    @PrimaryKey
    public int bookId;

    public String title;

    @ColumnInfo(name = "user_id")
    public int userId;
}
