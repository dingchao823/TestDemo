package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.R
import com.example.base.constants.Path
import kotlinx.android.synthetic.main.activity_lottie.*

@Route(path = Path.LOTTIE_ACTIVITY)
class LottieActivity : Activity(), View.OnClickListener {

    override fun onClick(v: View?) {
        animation_view1.playAnimation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)

        reset?.setOnClickListener {
            play()
        }
    }

    private fun play(){
        setLottieAnimation(animation_view1, "lottie_json/mine.zip")
        setLottieAnimation(animation_view2, "lottie_json/basket_1.json")
    }

    private fun setLottieAnimation(view : LottieAnimationView, json : String) {
        val composition = LottieCompositionFactory.fromAssetSync(this, json).value
        view.cancelAnimation()
        view.progress = 0f
        view.setComposition(composition!!)
        view.playAnimation()
    }

}