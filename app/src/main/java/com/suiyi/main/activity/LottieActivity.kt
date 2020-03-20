package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.R
import com.example.base.constants.Path
import kotlinx.android.synthetic.main.activity_lottie.*

@Route(path = Path.LOTTIE_ACTIVITY)
class LottieActivity : Activity(), View.OnClickListener {

    val ICON_TYPE_HOME = 1
    val ICON_TYPE_CATEGORY = 2
    var iconType = -1
    var iconTypeBackUp = -1

    val ICON_STATUS_IDLE = 0
    val ICON_STATUS_UNSELECTED_TOP = 1
    val ICON_STATUS_UNSELECTED_RECOMMEND = 2
    val ICON_STATUS_SELECTED_TOP = 3
    val ICON_STATUS_SELECTED_RECOMMEND = 4
    var status = ICON_STATUS_IDLE

    val staticResId = R.mipmap.ic_cart

    override fun onClick(v: View?) {
        animation_view1.playAnimation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lottie)

        iconType = ICON_TYPE_HOME
        selectHome(true)
        selectCategory(false)
        iconTypeBackUp = ICON_TYPE_HOME

        animation_view1.setOnClickListener {
            iconType = ICON_TYPE_HOME
            selectHome(true)
            selectCategory(false)
            iconTypeBackUp = ICON_TYPE_HOME
        }
        animation_view2.setOnClickListener {
            iconType = ICON_TYPE_CATEGORY
            selectHome(false)
            selectCategory(true)
            iconTypeBackUp = ICON_TYPE_CATEGORY
        }
    }

    private fun selectHome(selected : Boolean) {
        if (iconTypeBackUp == iconType){
            when (status) {
                ICON_STATUS_SELECTED_TOP -> {
                    status = ICON_STATUS_SELECTED_RECOMMEND
                    setLottieAnimation(animation_view1, "lottie_json/home_scroll_down.json")
                    playAnimation(animation_view1)
                }
                ICON_STATUS_SELECTED_RECOMMEND -> {
                    status = ICON_STATUS_SELECTED_TOP
                    setLottieAnimation(animation_view1, "lottie_json/home_scroll_up.json")
                    playAnimation(animation_view1)
                }
                else -> {
                }
            }
        }else {
            if (selected) {
                when (status) {
                    ICON_STATUS_IDLE -> {
                        status = ICON_STATUS_SELECTED_RECOMMEND
                        setLottieAnimation(animation_view1, "lottie_json/home_zoom_recommend.json")
                        playAnimation(animation_view1)
                    }
                    ICON_STATUS_UNSELECTED_TOP -> {
                        status = ICON_STATUS_SELECTED_TOP
                        setLottieAnimation(animation_view1, "lottie_json/home_zoom_top.json")
                        playAnimation(animation_view1)
                    }
                    ICON_STATUS_UNSELECTED_RECOMMEND -> {
                        status = ICON_STATUS_SELECTED_RECOMMEND
                        setLottieAnimation(animation_view1, "lottie_json/home_zoom_recommend.json")
                        playAnimation(animation_view1)
                    }
                    else -> {
                    }
                }
            } else {
                when (status){
                    ICON_STATUS_SELECTED_TOP -> {
                        status = ICON_STATUS_UNSELECTED_TOP
                    }
                    ICON_STATUS_SELECTED_RECOMMEND -> {
                        status = ICON_STATUS_UNSELECTED_RECOMMEND
                    }
                }
                animation_view1.setImageResource(staticResId)
            }
        }
    }

    private fun selectCategory(selected: Boolean){
        if (selected){
            animation_view2.setImageResource(R.mipmap.ic_function)
        }else{
            animation_view2.setImageResource(staticResId)
        }
    }

    private fun setLottieAnimation(view : LottieAnimationView, json : String) {
        val composition = LottieComposition.Factory.fromFileSync(this, json)
        view.cancelAnimation()
        view.progress = 0f
        view.setComposition(composition!!)
        view.playAnimation()

//        view.setAnimation(json)
//        view.repeatCount = 0
//        view.progress = 0f
    }

    fun playAnimation(animationView: LottieAnimationView?) {
        animationView?.playAnimation()
    }
}