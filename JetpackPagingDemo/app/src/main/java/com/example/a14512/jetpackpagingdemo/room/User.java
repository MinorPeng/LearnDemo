package com.example.a14512.jetpackpagingdemo.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

/**
 * @author 14512 on 2018/9/2
 */
@Entity(tableName = "user", indices = {@Index(value = {"first_name", "last_name"})})
public class User {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    /**
     * 嵌套对象
     */
    @Embedded
    public Address mAddress;

    private Date birthdy;

    @Ignore
    public User(String firstName, String lastName) {
        this.uid = Integer.parseInt(UUID.randomUUID().toString());
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String id, String firstName, String lastName) {
        this.uid = Integer.parseInt(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
