package com.example.base.burypoint

import android.app.Activity
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.base.burypoint.bean.ExpoBean
import java.util.concurrent.ConcurrentHashMap

private val debugMode = false

class ExpoHandlerHelper private constructor(){

    private val SHOW_TIME = 2000L  // 曝光时间

    /** 暂停曝光时产生的问题 **/
    private var pauseSystemTime : Long = -1

    /** 当前所在的曝光页面 **/
    private var currentExpoName : String = ""

    /** 存放数据，线程安全 **/
    private var mDataMap : ConcurrentHashMap<Int, ExpoBean> = ConcurrentHashMap()

    /** 等待池 **/
    private var mWaitingMap : ConcurrentHashMap<String, ConcurrentHashMap<Int, ExpoBean>> = ConcurrentHashMap()

    /** handler **/
    private val mHandler = ExpoHandler()

    /**
     * 当生命周期调用onStart时操作
     */
    fun addActivity(activity: Activity?) {
        currentExpoName = activity?.packageName + activity?.localClassName
    }

    /**
     * 当Fragment生命周期调用onStart时操作
     */
    fun addFragment(fragment : Fragment?){
        currentExpoName = fragment?.javaClass?.`package`?.name + fragment?.javaClass?.name
    }

    /**
     * 当生命周期调用onDestroy时操作
     */
    fun removeActivity(activity: Activity?) {
        if (currentExpoName.equals(activity?.packageName + activity?.localClassName)){
            currentExpoName = ""
        }
    }

    /**
     * 当Fragment生命周期调用onDestroy时操作
     */
    fun removeFragment(fragment: Fragment?){
        if (currentExpoName.equals(fragment?.javaClass?.`package`?.name + fragment?.javaClass?.name)){
            currentExpoName = ""
        }
    }

    /**
     * 添加数据到待曝光map
     */
    fun addExpoToMap(expoBean : ExpoBean?){
        if (expoBean == null || mDataMap[expoBean.expoId] != null){
            return
        }
        expoBean.showTime = System.currentTimeMillis()
        mDataMap.put(expoBean.expoId, expoBean)

        val message = Message()
        message.what = expoBean.expoId
        message.obj = expoBean
        mHandler.sendMessageDelayed(message, SHOW_TIME)

        if (debugMode) {
            Log.e("dc", "add expo [" + expoBean.expoId + "]")
        }
    }

    /**
     * 移除指定的埋点数据
     */
    fun removeExpoFromMap(expoId : Int?){
        if (expoId == null){
            return
        }
        mDataMap.remove(expoId)
        mHandler.removeMessages(expoId)

        if (debugMode) {
            Log.e("dc", "remove expo [$expoId]")
        }
    }

    /**
     * 移除所有埋点
     */
    fun removeAllExpo(activityName : String?){
        if (activityName == null || TextUtils.isEmpty(activityName)){
            return
        }
        if (debugMode) {
            Log.e("dc", "$activityName call removeAll")
        }
        if (activityName.equals(currentExpoName)) {
            mDataMap.clear()
            mHandler.removeCallbacksAndMessages(null)

            if (debugMode) {
                Log.e("dc", "$activityName true removeAll")
            }
        }
    }

    /**
     * 暂停曝光
     *
     * TODO 有些页面并不需要曝光埋点，所以这边要考虑优化掉那些不需要埋点的页面
     */
    fun pauseExpo(activityName : String?){
        if (activityName == null || TextUtils.isEmpty(activityName)){
            return
        }
        if (debugMode) {
            Log.e("dc", "$activityName call pause Expo")
        }

        // 将当前遍历池里面的数据放到等待池里面去
        if(mWaitingMap.get(activityName).isNullOrEmpty()){
            mWaitingMap.put(activityName, mDataMap)
            mDataMap = ConcurrentHashMap()
            mHandler.removeCallbacksAndMessages(null)

            if (debugMode) {
                Log.e("dc", "$activityName true pause pool and exchange pool !")
            }
        }
    }

    /**
     * 恢复曝光，重置刚刚的曝光
     */
    fun resumeExpo(activityName: String?){
        if (activityName == null || TextUtils.isEmpty(activityName)){
            return
        }
        if (debugMode) {
            Log.e("dc", "$activityName call resume Expo")
        }
        if(!mWaitingMap.get(activityName).isNullOrEmpty()){
            mDataMap.clear()
            mHandler.removeCallbacksAndMessages(null)
            // 将其从缓存池中数据重新引用到遍历池中
            mDataMap = mWaitingMap.get(activityName)!!
            // 将其从缓存池中移除
            mWaitingMap.remove(activityName)
            // 重新将其添加到消息队列
            var message : Message? = null
            for ((index, expoBean) in mDataMap){
                message = Message()
                message.what = expoBean.expoId
                message.obj = expoBean
                mHandler.sendMessageDelayed(message, SHOW_TIME)
            }
            if (debugMode) {
                Log.e("dc", "$activityName true resume pool and exchange pool")
            }
        }
    }

    companion object{

        var mapSc: HashMap<String, Any>? = HashMap()
        val instance = SingletonHolder.holder

        class ExpoHandler : Handler(){
            override fun handleMessage(msg: Message?) {
                if (msg == null) {
                    return
                }
                if (msg.obj is ExpoBean){
                    expo(msg.obj as ExpoBean)
                }
            }
        }

        /**
         * 曝光
         */
        private fun expo(expoBean : ExpoBean){
            mapSc?.put("pageType", expoBean.pageType)
            mapSc?.put("prePosition", expoBean.prePosition)
            mapSc?.put("page", expoBean.page)
            mapSc?.put("specialTopic", expoBean.specialTopic)
            mapSc?.put("activity", expoBean.activity)
            mapSc?.put("commodityID", expoBean.expoId)
            mapSc?.put("commodityName", expoBean.commodityName)
            mapSc?.put("perServingPrice", expoBean.pricePerCommodity)
            mapSc?.put("floorName", expoBean.floorName)
            mapSc?.put("floorRank", expoBean.floorRank)
            mapSc?.put("commodityRank", expoBean.commodityRank)
            if (!TextUtils.isEmpty(expoBean.keyWord)) {
                mapSc?.put("keyWord", expoBean.keyWord)
            }
            if (!TextUtils.isEmpty(expoBean.searchID)) {
                mapSc?.put("searchID", expoBean.searchID)
            }
            if (!TextUtils.isEmpty(expoBean.markCode)) {
                mapSc?.put("markCode", expoBean.markCode)
            }
            mapSc?.clear()

            if (debugMode) {
                Log.e("dc", "true expo [" + expoBean.expoId + "]")
            }
        }
    }

    private object SingletonHolder {
        val holder = ExpoHandlerHelper()
    }

}