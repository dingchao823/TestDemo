package com.example.base.burypoint


/**
 * 曝光埋点白名单，只有处于该名单内的 Activity 或者 Fragment 才会曝光
 */
object ExpoWhiteList{

    /**
     * 是否处于白名单中
     */
    fun isInWhiteList(item : Any?) : Boolean{
        if (item == null){
            return false
        }
        when(item){
        }
        return false
    }

}