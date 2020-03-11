package com.suiyi.main

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.tinkerpatch.sdk.TinkerPatch
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike


class SHApplication : Application() {

    var domainUrl: String? = ""

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        initSystemParams()
        initARouter()
        initTinker()
    }

    private fun initSystemParams() {
        val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        domainUrl = info.metaData?.getString("environment")
    }

    private fun initARouter() {
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

    private fun initTinker() {
        if (BuildConfig.TINKER_ENABLE) {

            // 我们可以从这里获得Tinker加载过程的信息
            val tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike()

            // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(1)

            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval()
        }
    }

    companion object {

        private var instance: Application? = null

        fun instance() = instance!!

    }
}