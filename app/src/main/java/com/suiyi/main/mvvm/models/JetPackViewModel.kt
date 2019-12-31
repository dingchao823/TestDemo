package com.suiyi.main.mvvm.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.base.bean.PagingBean
import com.suiyi.main.mvvm.source.PageDataSourceFactory


class JetPackViewModel : ViewModel() {

    var list: LiveData<PagedList<PagingBean>?>? = null

    fun initList(){
        val config = PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .setEnablePlaceholders(false)
                .build()
        list = LivePagedListBuilder(PageDataSourceFactory(), config).build()
    }

}