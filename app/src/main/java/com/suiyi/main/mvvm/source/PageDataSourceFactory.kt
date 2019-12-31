package com.suiyi.main.mvvm.source

import androidx.paging.DataSource
import com.example.base.bean.PagingBean


class PageDataSourceFactory : DataSource.Factory<Int, PagingBean>(){


    val source = PageDataSource()

    override fun create(): DataSource<Int, PagingBean> {
        return source
    }

}