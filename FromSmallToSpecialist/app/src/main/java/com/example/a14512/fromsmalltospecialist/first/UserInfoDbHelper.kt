package com.example.a14512.fromsmalltospecialist.first

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @author 14512 on 2018/3/24
 */
class UserInfoDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "userinfo.db"
        const val DB_VERSION = 1

        const val TABLE_USER_INFO = "userinfo"
        const val TABLE_COMPANY = "company"

        const val TEL_COLUMN = "tel_num"
        const val DESC_COLUMN = "desc"
        const val COMP_ID_COLUMN = "comp_id"
        const val ID_COLUMN = "id"
        const val BUSINESS_COLUMN = "business"
        const val ADDR_COLUMN = "addr"

        private const val POSTCODE_TABLE_SQL = "CREATE TABLE ${TABLE_USER_INFO} (${TEL_COLUMN} TEXT, " +
                "${COMP_ID_COLUMN} TEXT, ${DESC_COLUMN} TEXT)"
        private const val COMPANY_TABLE_SQL = "CREATE TABLE ${TABLE_COMPANY} (${ID_COLUMN} " +
                "TEXT PRIMARY KEY, ${BUSINESS_COLUMN} TEXT, ${ADDR_COLUMN} TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(POSTCODE_TABLE_SQL)
        db.execSQL(COMPANY_TABLE_SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}