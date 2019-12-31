package com.suiyi.main.activity

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.base.constants.Path


@Route(path = Path.WEBP_ACTIVITY)
class WebPActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val image = ImageView(this)
        image.layoutParams = ViewGroup.LayoutParams(-2, -2)
        setContentView(image)

        val circleCrop: Transformation<Bitmap?> = CircleCrop()

        var startTime = System.currentTimeMillis()
        Glide.with(this)
                .load("https://p.upyun.com/demo/webp/webp/animated-gif-2.webp")
                .optionalTransform(circleCrop)
                .optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(circleCrop))
                .into(object : SimpleTarget<Drawable?>(){
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        var endTime = System.currentTimeMillis()
                        Log.e("dc", "耗时：${endTime - startTime}")
                    }
                })
    }
}