package com.suiyi.main.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.base.bean.PagingBean
import com.suiyi.main.model.source.PageDataSourceFactory


class JetPackViewModel : ViewModel() {

    val sourceFactory = PageDataSourceFactory()

    var list: LiveData<PagedList<PagingBean>?>? = null
        get() {
            val config = PagedList.Config.Builder()
                    .setPageSize(20)
                    .setInitialLoadSizeHint(20)
                    .setEnablePlaceholders(false)
                    .build()
            field = LivePagedListBuilder(sourceFactory, config).build()
            return field
        }

}