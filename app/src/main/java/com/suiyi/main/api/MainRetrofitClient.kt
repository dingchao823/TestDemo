package com.suiyi.main.api

import com.example.base.network.BaseRetrofitClient
import com.suiyi.main.service.DownloadService
import okhttp3.ResponseBody
import retrofit2.Call

object MainRetrofitClient : BaseRetrofitClient(){

    override fun getBaseUrl(): String {
        return "https://user-gold-cdn.xitu.io/"
    }

    fun download(url : String) : Call<ResponseBody?>?{
        return getService(DownloadService::class.java).download(url)
    }

}