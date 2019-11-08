package com.suiyi.main.utils

import android.content.Context
import android.view.WindowManager

object DimenUtils {

    fun getDisplayWidth(context: Context) : Int{
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    fun getDisplayHeight(context: Context) : Int{
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.height
    }

    fun dipTopx(context: Context?, dpValue: Float): Int {
        val scale = context?.resources?.displayMetrics?.density ?: 1f
        return (dpValue * scale + 0.5f).toInt()
    }

    fun pxToDpF(context: Context, pxValue: Float) : Float{
        val scale = context.resources.displayMetrics.density
        return pxValue / scale + 0.5f
    }

}