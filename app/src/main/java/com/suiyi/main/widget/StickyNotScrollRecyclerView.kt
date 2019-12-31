package com.suiyi.main.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

open class StickyNotScrollRecyclerView : RecyclerView{

    private var downX : Float = 0f
    private var downY : Float = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(e)
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return true
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        val x = e?.x
        val y = e?.y
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x ?: 0f
                downY = y ?: 0f
                parent.requestDisallowInterceptTouchEvent(true)
                Log.e("dc", "StickyNotScrollRecyclerView >>> ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {//获取到距离差
                val dx: Float? = x?.minus(downX)
                val dy: Float? = y?.minus(downY)
                //通过距离差判断方向
                val orientation = getOrientation(dx ?: 0f, dy ?: 0f)
                when (orientation) {
                    "b", "t" -> {
                        Log.e("dc", "StickyNotScrollRecyclerView >>> 上下移动")
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(e)
    }

    private fun getOrientation(dx: Float = 0f, dy: Float = 0f): String {
        return if (abs(dx) > abs(dy)) {
            //X轴移动
            if (dx > 0) "r" else "l"//右,左
        } else {
            //Y轴移动
            if (dy > 0) "b" else "t"//下//上
        }
    }
}