package com.suiyi.main.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class NestedInnerRecyclerView : RecyclerView {

    private var downX : Float = 0f
    private var downY : Float = 0f

    constructor(context: Context?) : super(context!!) {}

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {}

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val x = e.x
        val y = e.y
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x
                downY = y
                // 必须加上这个，让 RecyclerView 也要处理滑动冲突才行
                parent.requestDisallowInterceptTouchEvent(true)
//                Log.e("dc", "inner ACTION_DOWN 》》》")
            }
            MotionEvent.ACTION_MOVE -> {
                val dx: Float? = x.minus(downX)
                val dy: Float? = y.minus(downY)
                //通过距离差判断方向
                val orientation = getOrientation(dx ?: 0f, dy ?: 0f)
                when (orientation) {
                    "r", "l" -> {
                        // 要求左右滑动很大才能触发父类的左右滑动
                        dx?.let {
                            if (abs(dx) > 100){
                                parent.requestDisallowInterceptTouchEvent(false)
//                                Log.e("dc", "inner ACTION_MOVE 》》》父类处理")
                                return false
                            }else{
                                parent.requestDisallowInterceptTouchEvent(true)
                            }
                        }
                    }
                    else -> {
                        parent.requestDisallowInterceptTouchEvent(true)
//                        Log.e("dc", "inner ACTION_MOVE 》》》子类处理")
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

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {
//        Log.e("dc", "Inner --> dispatchNestedScroll1[dxConsumed=$dxConsumed][dyConsumed=$dyConsumed][dxUnconsumed=$dxUnconsumed][dyUnconsumed=$dyUnconsumed][offsetInWindow=[$offsetInWindow]]")
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int): Boolean {
//        Log.e("dc", "Inner --> dispatchNestedScroll2[dxConsumed=$dxConsumed][dyConsumed=$dyConsumed][dxUnconsumed=$dxUnconsumed][dyUnconsumed=$dyUnconsumed][offsetInWindow=[$offsetInWindow]][type=$type]")
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
//        Log.e("dc", "Inner --> dispatchNestedPreFling[velocityX=$velocityX][velocityY=$velocityY]")
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
//        Log.e("dc", "Inner --> onStartNestedScroll[nestedScrollAxes=$nestedScrollAxes]")
        return super.onStartNestedScroll(child, target, nestedScrollAxes)
    }

    override fun onStopNestedScroll(child: View?) {
//        Log.e("dc", "Inner --> onStopNestedScroll")
        super.onStopNestedScroll(child)
    }
}