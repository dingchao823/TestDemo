package com.gem.tastyfood.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * 处理嵌套滑动
 *
 * @author 0004640
 */
class NestedInnerRecyclerView : RecyclerView {

    private var downX : Float = 0f
    private var downY : Float = 0f
    /** viewPager 左右滑动阈值 **/
    private val MOVE_RANGE = 100
    private var dx : Float? = 0f
    private var dy : Float? = 0f
    private var orientation: String? = ""
    private var isDebug = true
    private var mVelocityTracker: VelocityTracker? = null
    private var mMaxFlingVelocity : Int? = null
    private val vc: ViewConfiguration? = ViewConfiguration.get(context)

    constructor(context: Context) : super(context){}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        mMaxFlingVelocity = vc?.scaledMaximumFlingVelocity
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker?.addMovement(e)
        when(e?.action){
            MotionEvent.ACTION_UP -> {
                mVelocityTracker?.clear()
            }
        }
        return super.onInterceptTouchEvent(e)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker?.addMovement(e)
        val vtev: MotionEvent? = MotionEvent.obtain(e)
        var eventAddedToVelocityTracker = false
        when(e.action){
            MotionEvent.ACTION_DOWN -> {
                downX = e.x
                downY = e.y
                // 必须加上这个，不然外部的 OutRecycler 拦截，因为都是竖直滑动
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                dx = e.x.minus(downX)
                dy = e.y.minus(downY)
                //通过距离差判断方向
                orientation = getOrientation(dx ?: 0f, dy ?: 0f)
                when (orientation) {
                    "r", "l" -> {
                        dx?.let{
                            if (abs(it) >= MOVE_RANGE) {
                                // 左右滑动超出阈值，ViewPager 处理左右滑动事件
                                if(isDebug) {
                                    Log.e("dc", "inner -> 自己处理左右")
                                }
                                parent.requestDisallowInterceptTouchEvent(false)
                                return false
                            }
                        }
                    }
                    else -> {
                        // 上下方向，ViewPager 不处理
                        if(isDebug) {
                            Log.e("dc", "inner -> 交由【父类】处理上下")
                        }
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {

            }
        }

        if (!eventAddedToVelocityTracker) {
            mVelocityTracker!!.addMovement(vtev)
        }
        vtev?.recycle()

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
        if(isDebug) {
            Log.e("dc", "Inner --> dispatchNestedScroll1[dxConsumed=$dxConsumed][dyConsumed=$dyConsumed][dxUnconsumed=$dxUnconsumed]" +
                    "[dyUnconsumed=$dyUnconsumed][offsetInWindow=[$offsetInWindow]]")
        }
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int): Boolean {
        if(isDebug) {
            Log.e("dc", "Inner --> dispatchNestedScroll2[dxConsumed=$dxConsumed][dyConsumed=$dyConsumed]" +
                    "[dxUnconsumed=$dxUnconsumed][dyUnconsumed=$dyUnconsumed][offsetInWindow=[$offsetInWindow]][type=$type]")
        }
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        if(isDebug) {
            Log.e("dc", "Inner --> dispatchNestedPreFling[velocityX=$velocityX][velocityY=$velocityY]")
        }
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        if (isDebug) {
            Log.e("dc", "Inner --> onStartNestedScroll[nestedScrollAxes=$nestedScrollAxes]")
        }
        return super.onStartNestedScroll(child, target, nestedScrollAxes)
    }

    override fun onStopNestedScroll(child: View?) {
        if (isDebug) {
            Log.e("dc", "Inner --> onStopNestedScroll")
        }
        super.onStopNestedScroll(child)
    }
}