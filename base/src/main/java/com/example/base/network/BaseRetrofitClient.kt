package com.example.base.network

import com.example.base.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 基础 retrofit
 *
 */
abstract class BaseRetrofitClient {

    protected abstract fun getBaseUrl(): String
    protected open fun handleBuilder(builder: OkHttpClient.Builder){}
    protected open fun getTimeOut(): Long {
        return 5L
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            builder.addInterceptor(logging)
                    .connectTimeout(getTimeOut(), TimeUnit.SECONDS)

            handleBuilder(builder)

            return builder.build()
        }

    fun <S> getService(serviceClass: Class<S>): S {
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getBaseUrl())
                .build()
                .create(serviceClass)
    }

}