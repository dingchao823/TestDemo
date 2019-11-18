package com.suiyi.main

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
//import com.didichuxing.doraemonkit.DoraemonKit

class SHApplication : Application() {

    var domainUrl: String? = ""

    override fun onCreate() {
        super.onCreate()

        instance = this

        ARouter.openLog()     // 打印日志
        ARouter.openDebug()
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

        val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        domainUrl = info.metaData?.getString("environment")

//        DoraemonKit.install(this)
    }

    companion object {

        private var instance: Application? = null

        fun instance() = instance!!

    }
}