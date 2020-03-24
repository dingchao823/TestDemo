package com.suiyi.main.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import com.suiyi.main.R
import kotlinx.android.synthetic.main.activity_transition_test.*


@Route(path = Path.ACTIVITY_ANIMATION_TEST)
class TransitionTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_test)

        // 开始动画
        start.setOnClickListener {
            handleFinish()
        }

    }

    private fun handleFinish() {

//        val imageView = ImageView(this)
//        imageView.setImageBitmap(onCut(this))
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

        val intent = Intent(this, TransitionTestActivity2::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


    fun onCut(activity: Activity): Bitmap? { //获取window最底层的view
        val view = activity.window.decorView
        view.buildDrawingCache()
        //状态栏高度
        val rect = Rect()
        view.getWindowVisibleDisplayFrame(rect)
        val stateBarHeight: Int = rect.top
        val display = activity.windowManager.defaultDisplay
        //获取屏幕宽高
        val widths = display.width
        val height = display.height
        //设置允许当前窗口保存缓存信息
        view.isDrawingCacheEnabled = true
        //去掉状态栏高度
        val bitmap = Bitmap.createBitmap(view.drawingCache, 0, stateBarHeight, widths, height - stateBarHeight)
        view.destroyDrawingCache()
        return bitmap
    }
}
