package com.example.a14512.fromsmalltospecialist.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v7.app.AppCompatActivity
import com.example.a14512.fromsmalltospecialist.R
import com.example.a14512.fromsmalltospecialist.SsoAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mSsoAuth: SsoAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSSO.setOnClickListener({
            if (mSsoAuth == null) {
                bindSsoAuthService()
            } else {
                doSsoAuth()
            }
//            startActivity(Intent().setClass(this, com.example.a14512.fromsmalltospecialist.activity.ListActivity::class.java))
        })
        btnJunit.setOnClickListener({startActivity(Intent().setClass(this, ListActivity::class.java))})
//        val refreshListView = RefreshListView(this)
//        val dataStrings = arrayListOf<String>()
//        var i = 0
//        do {
//            dataStrings.add("item-$i")
//            i++
//        } while (i == 10)
//        refreshListView.adapter = ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, dataStrings)
//        refreshListView.setOnRefreshListener({
//            Toast.makeText(this, "refreshing", Toast.LENGTH_SHORT).show()
//        })
//        refreshListView.setOnLoadingListener({
//            Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
//        })
//        setContentView(refreshListView)

    }

    private fun doSsoAuth() {
        try {
            mSsoAuth!!.ssoAuth("Mr.Simple", "pwd123")
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    private fun bindSsoAuthService() {
        val intent = Intent()
        intent.action = "com.example.a14512.weatherkotlin.service.SinaSsoAuthService"
        intent.`package` = "com.example.a14512.weatherkotlin"
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    internal var mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mSsoAuth = SsoAuth.Stub.asInterface(service)
            doSsoAuth()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mSsoAuth = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnection)
    }
}
