package com.suiyi.main.activity

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suiyi.main.R
import com.suiyi.main.utils.AnimatorUtils
import com.suiyi.main.utils.AddCartAnimateUtils
import kotlinx.android.synthetic.main.activity_cart.*

/**
 * 购物车
 */
class CartActivity : AppCompatActivity(), AnimatorUtils.OnAnimatorListener{

    val addAnimate = AddCartAnimateUtils(this)

    override fun onAnimationEnd(animator: Animator) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        add_good.setOnClickListener{
            addAnimate.setViewParam(add_good, cart_view, getDrawable(R.drawable.drawable_cart), cart_view, 1.5f, 200)
            addAnimate.duration = 400
            addAnimate.startAnimate()
        }
    }
}