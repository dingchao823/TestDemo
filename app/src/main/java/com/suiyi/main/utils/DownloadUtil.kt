package com.suiyi.main.utils

import com.suiyi.main.api.MainRetrofitClient
import com.suiyi.main.interfaces.DownloadListener
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


object DownloadUtil {

    private const val sBufferSize = 8192

    fun download(url: String, path: String?, downloadListener: DownloadListener) {

        MainRetrofitClient.download(url)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                writeResponseToDisk(path, response, downloadListener)
            }

            override fun onFailure(call: Call<ResponseBody?>, throwable: Throwable) {
                downloadListener.onFail("网络错误～")
            }
        })
    }

    /**
     * 从response获取输入流以及总大小
     */
    private fun writeResponseToDisk(path: String?, response: Response<ResponseBody?>, downloadListener: DownloadListener) {
        writeFileFromIS(File(path), response.body()?.byteStream(), response.body()!!.contentLength(), downloadListener)
    }

    /**
     * 将输入流写入文件
     */
    private fun writeFileFromIS(file: File, `is`: InputStream?, totalLength: Long, downloadListener: DownloadListener) {
        //开始下载
        downloadListener.onStart()
        //创建文件
        if (!file.exists()) {
            if (!file.parentFile.exists()) {
                file.parentFile.mkdir()
            }
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                downloadListener.onFail("createNewFile IOException")
            }
        }
        var os: OutputStream? = null
        var currentLength: Long = 0
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            val data = ByteArray(sBufferSize)
            var len = 0
            while (`is`?.read(data, 0, sBufferSize).also { len = it ?: 0 } != -1) {
                os.write(data, 0, len)
                currentLength += len.toLong()
                //计算当前下载进度
                downloadListener.onProgress((100 * currentLength / totalLength).toInt())
            }
            //下载完成，并返回保存的文件路径
            downloadListener.onFinish(file.getAbsolutePath())
        } catch (e: IOException) {
            e.printStackTrace()
            downloadListener.onFail("IOException")
        } finally {
            try {
                `is`?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}