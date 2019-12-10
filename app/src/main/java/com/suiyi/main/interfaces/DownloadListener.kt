package com.suiyi.main.interfaces

abstract class DownloadListener {

    open fun onStart(){

    }

    open fun onProgress(progress: Int){

    }

    open fun onFinish(path: String?){

    }

    open fun onFail(errorInfo: String?){

    }
}