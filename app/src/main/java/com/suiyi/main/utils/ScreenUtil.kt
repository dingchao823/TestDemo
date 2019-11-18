package com.suiyi.main.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.suiyi.main.SHApplication


object ScreenUtil {

    fun getScreenHeight() : Int{
        val wm = (SHApplication.instance().getSystemService(Context.WINDOW_SERVICE)) as WindowManager
        wm?.let {
            return wm.defaultDisplay?.height ?: -1
        }
    }

    fun getScreenWidth() : Int{
        val wm = (SHApplication.instance().getSystemService(Context.WINDOW_SERVICE)) as WindowManager
        wm?.let {
            return wm.defaultDisplay?.width ?: -1
        }
    }

    fun getStatusBarHeight() : Int{
        var height = -1
        val resourceId = SHApplication.instance().resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = SHApplication.instance().resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    fun getRealScreenHeight(activity: Activity): Int {
        val outMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getRealMetrics(outMetrics)
        return outMetrics.heightPixels
    }
}