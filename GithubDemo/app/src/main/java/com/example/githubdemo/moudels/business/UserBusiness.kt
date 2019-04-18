package com.example.githubdemo.moudels.business

import android.arch.persistence.room.*
import com.example.githubdemo.base.AppDatabase
import com.example.githubdemo.base.Global
import com.example.githubdemo.moudels.User

/**
 * @author 14512 on 2019/4/18
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: List<User>)

    @Update
    fun updateUser(users: List<User>)

    @Delete
    fun deleteUser(users: List<User>)

    @Query("SELECT * FROM users where id = :userId")
    fun getUser(userId: String): User?

    @Query("SELECT * FROM users where name LIKE :name ORDER BY name LIMIT :page, 30")
    fun getUsers(name: String, page: Int): List<User>?
}

class UserRepository private constructor(private val userDao: UserDao) {

    companion object {
        val INSTANCE: UserRepository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            UserRepository(AppDatabase.getInstance(Global.getContext()).userDao())
        }
    }

    fun getUser(userId: String): User? = userDao.getUser(userId)

    fun getUsers(name: String, page: Int): List<User>? = userDao.getUsers(name, page)

    fun updateUser(users: List<User>) = userDao.updateUser(users)

    fun insertUser(users: List<User>) = userDao.insertUser(users)

    fun deleteUser(users: List<User>) = userDao.deleteUser(users)
}