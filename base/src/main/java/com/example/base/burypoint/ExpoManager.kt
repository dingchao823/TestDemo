package com.example.base.burypoint

import android.app.Activity
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.example.base.bean.Goods
import com.example.base.burypoint.bean.ExpoBean
import com.example.base.burypoint.items.expoStart

sealed class ExpoMode
/** handler 模式 **/
class HandlerMode : ExpoMode()
/** 线程模式 **/
class ThreadMode : ExpoMode()

val expoMode : ExpoMode = HandlerMode()

/**
 * 埋点管理器
 *
 */
class ExpoManager{

    companion object{
        /**
         * 埋点
         */
        fun expoStart(item : Any?, position : Int?, params : Map<String, Any>?){
            if (item == null || position == null || params == null){
                return
            }
            when(item){
                // 测试代码
                is String -> {
                    val model = ExpoBean()
                    model.expoId = position
                    ExpoThreadHelper.addExpoToMap(model)
                }
                // 商品
                is Goods -> {
                    item.expoStart(position, params)
                }
            }
        }

        /**
         * 移除埋点
         */
        fun expoEnd(expoId : Int?){
            if (expoId == null){
                return
            }
            when(expoMode){
                is HandlerMode -> {
                    ExpoHandlerHelper.instance.removeExpoFromMap(expoId)
                }
                is ThreadMode -> {
                    ExpoThreadHelper.removeExpoFromMap(expoId)
                }
            }
        }

        /**
         * 移除所有
         */
        fun removeAllExpo(activityName : String?){
            if (activityName == null || TextUtils.isEmpty(activityName)){
                return
            }
            when(expoMode){
                is HandlerMode -> {
                    ExpoHandlerHelper.instance.removeAllExpo(activityName)
                }
                is ThreadMode -> {
                    ExpoThreadHelper.removeAllExpo(activityName)
                }
            }
        }

        /**
         * 暂停曝光
         */
        fun pauseExpo(activityName : String?){
            if (activityName == null || TextUtils.isEmpty(activityName)){
                return
            }
            when(expoMode){
                is HandlerMode -> {
                    ExpoHandlerHelper.instance.pauseExpo(activityName)
                }
                is ThreadMode -> {
                    ExpoThreadHelper.pauseExpo(activityName)
                }
            }
        }

        /**
         * 恢复曝光
         */
        fun resumeExpo(activityName : String?){
            if (activityName == null || TextUtils.isEmpty(activityName)){
                return
            }
            when(expoMode){
                is HandlerMode -> {
                    ExpoHandlerHelper.instance.resumeExpo(activityName)
                }
                is ThreadMode -> {
                    ExpoThreadHelper.resumeExpo(activityName)
                }
            }
        }

        /**
         * 获取 Fragment 曝光的唯一名称
         */
        fun getFragmentExpoName(fragment: Fragment?) : String
                = fragment?.javaClass?.name + "." + fragment?.hashCode()

        /**
         * 获取 Activity 曝光的唯一名称
         */
        fun getActivityExpoName(activity: Activity?)
                = activity?.javaClass?.name  + "." + activity?.hashCode()

        /**
         * 当生命周期调用onStart时操作
         */
        fun addActivity(activity: Activity?) {
            when(expoMode){
                is HandlerMode -> {
                    ExpoHandlerHelper.instance.addActivity(activity)
                }
                is ThreadMode -> {
                    ExpoThreadHelper.addActivity(activity)
                }
            }
        }

        /**
         * 当Fragment生命周期调用onStart时操作
         */
        fun addFragment(fragment : Fragment?){
            when(expoMode){
                is HandlerMode -> {
                    ExpoHandlerHelper.instance.addFragment(fragment)
                }
                is ThreadMode -> {
                    ExpoThreadHelper.addFragment(fragment)
                }
            }
        }

        /**
         * 当生命周期调用onDestroy时操作
         */
        fun removeActivity(activity: Activity?) {
            when(expoMode){
                is HandlerMode -> {
                    ExpoHandlerHelper.instance.removeActivity(activity)
                }
                is ThreadMode -> {
                    ExpoThreadHelper.removeActivity(activity)
                }
            }
        }

        /**
         * 当Fragment生命周期调用onDestroy时操作
         */
        fun removeFragment(fragment: Fragment?){
            when(expoMode){
                is HandlerMode -> {
                    ExpoHandlerHelper.instance.removeFragment(fragment)
                }
                is ThreadMode -> {
                    ExpoThreadHelper.removeFragment(fragment)
                }
            }
        }
    }

}