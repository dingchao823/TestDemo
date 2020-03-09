package com.suiyi.main.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import com.suiyi.main.R

@Route(path = Path.DECOR_VIEW_ACTIVITY)
class DecorViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_such_a_thing)

        val decorView = window.decorView.findViewById<FrameLayout>(android.R.id.content)

        val imageView = ImageView(this)
        imageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageResource(R.mipmap.image_second_floor)

        decorView.postDelayed({
            decorView.addView(imageView)

            val objectAnimator1 = ObjectAnimator.ofFloat(imageView,"scaleX", 1f, 1.5f)
            val objectAnimator2 = ObjectAnimator.ofFloat(imageView,"scaleY", 1f, 1.5f)
            val objectAnimator3 = ObjectAnimator.ofFloat(imageView,"alpha", 1f, 0f)
            val set = AnimatorSet()
            set.duration = 3000
            set.playTogether(objectAnimator1,objectAnimator2,objectAnimator3)
            set.start()
            set.addListener(object : Animator.AnimatorListener{

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    decorView.removeView(imageView)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })

        }, 2000)



    }
}
