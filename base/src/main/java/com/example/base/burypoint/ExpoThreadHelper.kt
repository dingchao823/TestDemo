package com.example.base.burypoint

import android.app.Activity
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.example.base.burypoint.bean.ExpoBean
import java.util.concurrent.ConcurrentHashMap

/**
 * 曝光埋点方案 线程存取方案
 */
object ExpoThreadHelper{

    private val SHOW_TIME = 2000  // 曝光时间
    private var mThread : Thread? = null

    /** 暂停曝光时产生的问题 **/
    private var pauseSystemTime : Long = -1

    /** 当前所在的曝光页面 **/
    private var currentExpoName : String = ""

    /** 存放数据，线程安全 **/
    private var mDataMap : ConcurrentHashMap<Int, ExpoBean> = ConcurrentHashMap()

    /** 等待池 **/
    private var mWaitingMap : ConcurrentHashMap<String, ConcurrentHashMap<Int, ExpoBean>> = ConcurrentHashMap()

    private val mapSc: HashMap<String, Any>? = HashMap()

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
        if (expoBean == null){
            return
        }
        expoBean.showTime = System.currentTimeMillis()
        mDataMap.put(expoBean.expoId, expoBean)
        startThread()

    }

    /**
     * 移除指定的埋点数据
     */
    fun removeExpoFromMap(expoId : Int){
        mDataMap.remove(expoId)

    }

    /**
     * 移除所有埋点
     */
    fun removeAllExpo(activityName : String?){
        if (activityName.equals(currentExpoName)) {
            mDataMap.clear()
        }
    }

    /**
     * 暂停曝光
     */
    fun pauseExpo(activityName : String){
        // 将当前遍历池里面的数据放到等待池里面去
        if(mWaitingMap.get(activityName).isNullOrEmpty()){
            mWaitingMap.put(activityName, mDataMap)
            mDataMap = ConcurrentHashMap()

        }

    }

    /**
     * 恢复曝光，重置刚刚的曝光
     */
    fun resumeExpo(activityName: String){
        if(!mWaitingMap.get(activityName).isNullOrEmpty()){
            mDataMap.clear()
            // 将其从缓存池中数据重新引用到遍历池中
            mDataMap = mWaitingMap.get(activityName)!!
            // 将其从缓存池中移除
            mWaitingMap.remove(activityName)
            for ((index, expoBean) in mDataMap){
                expoBean.showTime = System.currentTimeMillis()
            }

        }

    }

    /**
     * 开启线程
     */
    private fun startThread(){
        if (mThread != null) {
            return
        }
        mThread = Thread(Runnable {
            while (true){
                if(mDataMap.isNullOrEmpty()){
                    continue
                }
                for ((index, expoBean) in mDataMap){
                    if (System.currentTimeMillis() - expoBean.showTime > SHOW_TIME){
                        expo(expoBean)
                        mDataMap.remove(index)
                    }
                }
            }
        })
        mThread?.start()
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

    }

}