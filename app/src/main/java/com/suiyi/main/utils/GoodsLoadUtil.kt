package com.suiyi.main.utils

import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.core.util.Pools
import androidx.lifecycle.MutableLiveData
import com.suiyi.main.activity.RecyclerLoadMoreActivity
import com.suiyi.main.adapter.ClassifyAreaGoodsAdapter2
import com.suiyi.main.bean.PoolBean
import com.suiyi.main.constants.ADAPTER_STATUS
import com.suiyi.main.constants.Network
import com.suiyi.main.constants.POOL_TYPE
import com.suiyi.main.interfaces.NetworkStateCallBack
import com.suiyi.main.mvvm.models.RvLoadMoreViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoodsLoadUtil {

    /** 当前加载的状态，与适配器中的一致 **/
    var loadingStatus = MutableLiveData<Int>()

    /** 数据 **/
    var mutablelist = MutableLiveData<ArrayList<PoolBean>>()

    var prePageIndex = -1
    var currentPageIndex = 0

    var myHandler : MyHandler = MyHandler()

    /**
     * 加载数据
     */
    fun startLoadData(){

        // 不会重新请求当前页面数据
        if (prePageIndex == currentPageIndex){
            Log.e("dc", "当前任务正在进行中")
            return
        }
        prePageIndex = currentPageIndex
        if (currentPageIndex == 0){

        }

        // 开始加载数据
        startLoadDataFromNet()
    }

    /**
     * 从网络里面获取到数据
     */
    private fun startLoadDataFromNet(){
        loadingStatus.postValue(ADAPTER_STATUS.ADAPTER_STATUS_LOADING)
        myHandler.postDelayed({
            val dataList = ArrayList<PoolBean>(20)
            for (index in 1..20){
                val bean =
                dataList.add(PoolBean(POOL_TYPE.product, "第${currentPageIndex}页，第${index}条数据"))
            }
            handleSuccess(dataList)
        }, 1000)
    }

    private fun handleFailed() {
        prePageIndex = currentPageIndex - 1
        loadingStatus.postValue(ClassifyAreaGoodsAdapter2.ADAPTER_STATUS_LOADING_FAIL)
    }

    private fun handleSuccess(list : ArrayList<PoolBean>){
        Log.e("dc", "startLoadDataFromNet >>>>>> network response [pre-pageIndex = ${currentPageIndex}]")
        if (currentPageIndex <= 1){
            loadingStatus.postValue(ClassifyAreaGoodsAdapter2.ADAPTER_STATUS_LOADING)
            mutablelist.postValue(list)
            currentPageIndex++
            Log.e("dc", "startLoadDataFromNet >>>>>> network response [pageIndex = $currentPageIndex]")
        }else{
            loadingStatus.postValue(ClassifyAreaGoodsAdapter2.ADAPTER_STATUS_LOADING_COMPLETE)
            mutablelist.postValue(list)
            Log.e("dc", "startLoadDataFromNet >>>>>> 没有下一页了1 [pageIndex = $currentPageIndex]")
        }
    }

    class MyHandler : Handler(){
        override fun handleMessage(msg: Message?) {

        }
    }

}