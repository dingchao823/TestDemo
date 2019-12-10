package com.suiyi.main.activity

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.suiyi.main.R
import com.suiyi.main.interfaces.DownloadListener
import com.suiyi.main.utils.DownloadUtil
import kotlinx.android.synthetic.main.activity_download_skin.*
import kotlinx.android.synthetic.main.activity_lottie.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException


/**
 * 下载皮肤包到 sd 卡位置之后替换到文件中测试，搭配 Lottie 使用
 *
 */
@Route(path = com.suiyi.main.constants.Path.DOWNLOAD_SKIN_ACTIVITY)
class DownloadSkinBagActivity : Activity(){

    val img0 = "2019/11/28/16eb10d2a74a9c30?w=46&h=47&f=png&s=1466"
    val img0Name = "16eb10d2a74a9c30"
    val img1 = "2019/11/28/16eb10d6f629e3dd?w=46&h=48&f=png&s=1353"
    val img1Name = "16eb10d6f629e3dd"
    val img2 = "2019/11/28/16eb10d80c31a873?w=76&h=76&f=png&s=1477"
    val img2Name = "16eb10d80c31a873"
    val PATH = Environment.getExternalStorageDirectory().absolutePath + File.separator + "SHApplication"

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_download_skin)

        initAnimation()

        DownloadUtil.download(img0, PATH + File.separator + "img_0.png", DownloadMe("img_0"))
        DownloadUtil.download(img1, PATH + File.separator + "img_1.png", DownloadMe("img_1"))
        DownloadUtil.download(img2, PATH + File.separator + "img_2.png", DownloadMe("img_2"))

        animation_view_0.setOnClickListener {
            if (it is LottieAnimationView){
                it.playAnimation()
            }
        }
    }

    private fun initAnimation(){
        animation_view_0.setAnimationFromJson("")
        animation_view_0.repeatCount = 0
        animation_view_0.repeatMode = LottieDrawable.RESTART
        animation_view_0.setImageAssetDelegate{
            BitmapFactory.decodeResource(resources, R.mipmap.ic_image_2)
        }
    }

    private fun resetAnimation(){
        animation_view_0.setAnimation("home.json")
        animation_view_0.repeatCount = 0
        animation_view_0.repeatMode = LottieDrawable.RESTART
        animation_view_0.setImageAssetDelegate {
            var bitmap : Bitmap? = null
            when(it.id){
                "image_0" -> {
                    bitmap = getImageFromFilePath(PATH + File.separator + "img_0.png")
                }
                "image_1" -> {
                    bitmap = getImageFromFilePath(PATH + File.separator + "img_1.png")
                }
                "image_2" -> {
                    bitmap = getImageFromFilePath(PATH + File.separator + "img_2.png")
                }
            }
            bitmap
        }
    }

    private fun getImageFromOnline(url : String) : Bitmap?{
        var returnBitmap : Bitmap? = null
        Glide.with(this).asBitmap().load(url).into(object : CustomTarget<Bitmap>(){
            override fun onLoadCleared(placeholder: Drawable?) {

            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                returnBitmap = resource
            }
        })
        return returnBitmap
    }

    private fun getImageFromFilePath(filePath : String) : Bitmap?{
        var bitmap: Bitmap? = null
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream(filePath)
            bitmap = BitmapFactory.decodeStream(fileInputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (bitmap == null) {
                    bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
                }
                fileInputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return bitmap
    }

    @Synchronized private fun stepOne(){
        count++
    }

    inner class DownloadMe(val name : String = "") : DownloadListener(){

        override fun onStart() {
            Log.e("dc", "download $name start!!")
        }

        override fun onProgress(progress: Int) {
            Log.e("dc", "download $name : $progress%")
        }

        override fun onFinish(path: String?) {
            Log.e("dc", "download $name end!!")
            stepOne()
            if (count == 3){
                resetAnimation()
            }
        }

        override fun onFail(errorInfo: String?) {

        }
    }
}