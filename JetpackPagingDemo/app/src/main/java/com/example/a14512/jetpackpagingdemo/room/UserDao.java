package com.example.a14512.jetpackpagingdemo.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * 数据访问对象
 * @author 14512 on 2018/9/2
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllById(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first " +
            "AND last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(List<User> users);

    /**
     *  @Insert的参数存在冲突时， 可以设置 onConflict属性的值来定义冲突的解决策略
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(User... users);

    @Delete
    void deleteUser(User user);

    @Update
    public void updateUsers(List<User> users);

    /**
     * 查询某一个字段的值
     */
    @Query("SELECT first_name, last_name FROM user")
    public List<NameTuple> loadFullName();
}
