package com.suiyi.main.mvvm.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.base.bean.PagingBean
import kotlinx.coroutines.*


class PageDataSource : PageKeyedDataSource<Int, PagingBean>() {

    private lateinit var loadParams : LoadParams<Int?>
    private lateinit var callBack : LoadCallback<Int?, PagingBean?>
    val loadingState = MutableLiveData<Int>()

    /** 页数 **/
    var pageIndex = 5

    override fun loadInitial(params: LoadInitialParams<Int?>, callback: LoadInitialCallback<Int?, PagingBean?>) {
        Log.e("dc", "PageDataSource${hashCode()} loadInitial")
        getPageData(0, object : DataSourceCallback {
            override fun listItems(list: List<PagingBean>, nextKey: Int) {
                callback.onResult(list, null, nextKey)
            }
        }, true)
    }

    override fun loadBefore(params: LoadParams<Int?>, callback: LoadCallback<Int?, PagingBean?>) {

    }

    override fun loadAfter(params: LoadParams<Int?>, callback: LoadCallback<Int?, PagingBean?>) {

        Log.e("dc", "PageDataSource${hashCode()}  loadAfter >>>>>>>>>> loading")
        loadParams = params
        callBack = callback

        getPageData(params.key, object : DataSourceCallback {
            override fun listItems(list: List<PagingBean>, nextKey: Int) {

                if (params.key == pageIndex){
                    loadingState.postValue(1003)
                    callback.onResult(list, null)
                }else {
                    loadingState.postValue(1002)
                    callback.onResult(list, nextKey)
                }
            }
        }, false)
    }

    /**
     * 重试
     */
    fun retryAfter(){
        loadAfter(loadParams, callBack)
    }

    private fun getPageData(i: Int, callback: DataSourceCallback, isInitial : Boolean) {
        GlobalScope.launch(Dispatchers.Default) {

            Log.e("dc", "GlobalScope${hashCode()}  ${if (isInitial) "loadInitial" else "loadAfter"} >>>>>>>>>> delay start")
            delay(3000L)
            Log.e("dc", "GlobalScope${hashCode()}  ${if (isInitial) "loadInitial" else "loadAfter"} >>>>>>>>>> delay end")

            val list: ArrayList<PagingBean> = ArrayList()
            for (index in 1..10){
                list.add(PagingBean("PageDataSource${hashCode()}  position $index"))
            }
            callback.listItems(list, i + 1)
        }
    }

    interface DataSourceCallback {
        fun listItems(list: List<PagingBean>, nextKey: Int)
    }

}