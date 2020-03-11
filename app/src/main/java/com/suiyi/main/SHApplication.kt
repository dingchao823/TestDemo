package com.suiyi.main

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import android.text.TextUtils
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.tinkerpatch.sdk.TinkerPatch
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


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
        initBugly(true)
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

    /**
     * 开启 Bugly
     *
     * @param isOpenLog 是否开启日志
     */
    @Suppress("SameParameterValue")
    private fun initBugly(isOpenLog : Boolean){
        val context = applicationContext
        // 获取当前包名
        val packageName = context.packageName
        // 获取当前进程名
        val processName = getProcessName(Process.myPid())
        // 设置是否为上报进程
        val strategy = UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        CrashReport.initCrashReport(applicationContext, BuildConfig.BUGLY_ID, isOpenLog)
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName: String = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }

    companion object {

        private var instance: Application? = null

        fun instance() = instance!!

    }
}