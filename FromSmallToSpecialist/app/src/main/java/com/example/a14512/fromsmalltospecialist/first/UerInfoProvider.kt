package com.example.a14512.fromsmalltospecialist.first

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import java.lang.IllegalArgumentException

/**
 * @author 14512 on 2018/3/24
 */
class UerInfoProvider : ContentProvider() {

    val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    companion object {
        private const val CONTENT = "content://"
        const val AUTHORIY = "com.example.a14512.fromsmalltospecialist"
        /**
         * 该provider返回的数据类型定义、数据集合
         */
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.${AUTHORIY}"

        /**
         * 单项数据
         */
        const val CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.${AUTHORIY}"

        /**
         * 数据集合操作时的Uri
         */
        val POSTCODE_URI: Uri = Uri.parse(CONTENT + AUTHORIY +"/"+ UserInfoDbHelper.TABLE_COMPANY)

        private lateinit var mDatabase: SQLiteDatabase

        const val USER_INFOS = 1
        const val USER_INFO_ITEM = 2
        const val COMPANY = 3
        const val COMPANY_ITEM = 4

    }



    private fun static() {
        uriMatcher.addURI(AUTHORIY, "userinfo", USER_INFOS)
        uriMatcher.addURI(AUTHORIY, "userinfo/*", USER_INFO_ITEM)
        uriMatcher.addURI(AUTHORIY, "company", COMPANY)
        uriMatcher.addURI(AUTHORIY, "company/#", COMPANY_ITEM)
    }


    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        var newId: Long = 0
        var newUri: Uri? = null
        when (uriMatcher.match(uri)) {
            USER_INFOS -> {
                newId = mDatabase.insert(UserInfoDbHelper.TABLE_USER_INFO, null, values)
                newUri = Uri.parse(CONTENT + AUTHORIY + "/${UserInfoDbHelper.TABLE_USER_INFO}/$newId")
            }
            COMPANY -> {
                newId = mDatabase.insert(UserInfoDbHelper.TABLE_COMPANY, null, values)
                newUri = Uri.parse(CONTENT + AUTHORIY + "/${UserInfoDbHelper.TABLE_COMPANY}/$newId")
            }
        }
        if (newId > 0) {
            return newUri!!
        }
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        var cursor: Cursor? = null
        when (uriMatcher.match(uri)) {
            USER_INFOS -> mDatabase.query(UserInfoDbHelper.TABLE_USER_INFO, projection, selection,
                    selectionArgs, null, null, sortOrder)
            USER_INFO_ITEM -> {
                var tel: String = uri!!.pathSegments[1]
                mDatabase.query(UserInfoDbHelper.TABLE_USER_INFO, projection, "tel_num = ?",
                        arrayOf(tel), null, null, sortOrder)
            }
            COMPANY -> mDatabase.query(UserInfoDbHelper.TABLE_COMPANY, projection, selection,
                    selectionArgs, null, null, sortOrder)
            COMPANY_ITEM -> {
                var cid: String = uri!!.pathSegments[1]
                mDatabase.query(UserInfoDbHelper.TABLE_COMPANY, projection, "id = ?",
                        arrayOf(cid), null, null, sortOrder)
            }
        }
        return cursor!!
    }

    override fun onCreate(): Boolean {
        mDatabase = UserInfoDbHelper(context).writableDatabase
        return true
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getType(uri: Uri?): String {
        return when (uriMatcher.match(uri)) {
            USER_INFOS, COMPANY -> CONTENT_TYPE
            USER_INFO_ITEM, COMPANY_ITEM -> CONTENT_TYPE_ITEM
            else -> throw RuntimeException("错误的Uri")
        }
    }
}