package com.suiyi.main.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import com.suiyi.main.R
import kotlinx.android.synthetic.main.activity_transition_test_2.*


@Route(path = Path.ACTIVITY_ANIMATION_TEST)
class TransitionTestActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_test_2)

        handleFinish()
    }

    private fun handleFinish() {

//        val imageView = ImageView(this)
//        imageView.setImageResource(R.mipmap.image_4)
//        val decorViewContent : ViewGroup? = window.decorView.findViewById(android.R.id.content)
//        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT)
//        imageView.scaleType = ImageView.ScaleType.FIT_XY
//        decorViewContent?.addView(imageView)
//
//        val objectAnimator1 = ObjectAnimator.ofFloat(imageView,"scaleX", 1f, 1.5f)
//        val objectAnimator2 = ObjectAnimator.ofFloat(imageView,"scaleY", 1f, 1.5f)
//        val objectAnimator3 = ObjectAnimator.ofFloat(imageView,"alpha", 1f, 0f)
//        val set = AnimatorSet()
//        set.duration = 5000
//        set.playTogether(objectAnimator1,objectAnimator2,objectAnimator3)
//        set.start()
//        set.addListener(object : Animator.AnimatorListener{
//
//            override fun onAnimationRepeat(animation: Animator?) {
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//                decorViewContent?.removeView(imageView)
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//            }
//
//            override fun onAnimationStart(animation: Animator?) {
//            }
//
//        })
    }
}
