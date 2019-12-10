package com.suiyi.main.model.source

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.base.bean.PagingBean


class PageDataSource : PageKeyedDataSource<Int, PagingBean>() {

    var isEnableLoading = false

    override fun loadInitial(params: LoadInitialParams<Int?>, callback: LoadInitialCallback<Int?, PagingBean?>) {
        Log.e("dc", "loadInitial before")
        if (!isEnableLoading){
            return
        }
        Log.e("dc", "loadInitial after")
        getPageData(1, object : dataSourceCallback {
            override fun listItems(list: List<PagingBean>, nextKey: Int) {
                callback.onResult(list, 1, Int.MAX_VALUE, 0, nextKey)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int?>, callback: LoadCallback<Int?, PagingBean?>) {

    }

    override fun loadAfter(params: LoadParams<Int?>, callback: LoadCallback<Int?, PagingBean?>) {
        Log.e("dc", "loadAfter before")
        if (!isEnableLoading){
            return
        }
        Log.e("dc", "loadAfter after")
        getPageData(params.key, object : dataSourceCallback {
            override fun listItems(list: List<PagingBean>, nextKey: Int) {
                callback.onResult(list, nextKey)
            }
        })
    }

    private fun getPageData(i: Int, callback: dataSourceCallback) {
        val list: ArrayList<PagingBean> = ArrayList()
        for (index in 1..20){
            list.add(PagingBean("你好$index"))
        }
        callback.listItems(list, i + 1)
    }


    interface dataSourceCallback {
        fun listItems(list: List<PagingBean>, nextKey: Int)
    }
}