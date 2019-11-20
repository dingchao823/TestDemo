package com.gem.tastyfood.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewParent
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.recyclerview.widget.RecyclerView
import com.suiyi.main.utils.DimenUtils
import com.suiyi.main.utils.ScreenUtil
import com.suiyi.main.widget.RecyclerTabLayout
import kotlin.math.abs

/**
 * 处理嵌套滑动，外层
 *
 * @author 0004640
 *
 * isLayoutFrozen ：对于 RecyclerTabLayout ，如果吸顶了，如果当前1号不在顶部，没办法下滑
 */
class NestedOuterRecyclerView : RecyclerView, NestedScrollingParent2 {

    private val mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private var mNestedScrollingTarget: View? = null
    private var mNestedScrollingChildView: View? = null
    /** ViewPager 固定的高度  */
    var viewPagerStickyHeight = 0
    var viewPagerBottomHeight = -1
    /** 误差范围 **/
    private val offset = 5
    private val childLocation = IntArray(2)
    private var downX : Float = 0f
    private var downY : Float = 0f
    private var velocityX : Float = 0f
    private var velocityY : Float = 0f
    private var isDebug = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        val x = e?.x
        val y = e?.y
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x ?: 0f
                downY = y ?: 0f
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {//获取到距离差
                val dx: Float? = x?.minus(downX)
                val dy: Float? = y?.minus(downY)
                //通过距离差判断方向
                val orientation = getOrientation(dx ?: 0f, dy ?: 0f)
                when (orientation) {
                    "r", "l" -> {
                        return false
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(e)
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

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        if (isDebug) {
            Log.e("dc", "Outer --> onStartNestedScroll 》》")
        }
        if (child is NestedInnerRecyclerView){
            child.onFlingListener = object : OnFlingListener() {
                override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                    Log.e("dc", "inner --> OnFlingListener 》》【dxConsumed=$velocityX】【dyConsumed=$velocityY】")
                    return false
                }
            }
        }
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
        mNestedScrollingTarget = target
        mNestedScrollingChildView = child
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        if(isDebug) {
            Log.e("dc", "Outer --> onStopNestedScroll 》》")
        }
        mNestedScrollingParentHelper.onStopNestedScroll(target, type)
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        if (isDebug) {
            Log.e("dc", "Outer --> onNestedScroll 》》【dxConsumed=$dxConsumed】【dyConsumed=$dyConsumed】【dxUnconsumed=$dxUnconsumed】【dyUnconsumed=$dyUnconsumed】")
        }
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        mNestedScrollingChildView?.let {
            when(target){
                is NestedInnerRecyclerView -> {
                    handleInnerRecyclerView(it, dx, dy, consumed, target)
                }
                is RecyclerTabLayout -> {
                    if (isDebug) {
                        Log.e("dc", "Outer --> onNestedPreScroll 》》处理 TabLayout 滑动【dy=$dy】")
                    }
                }
            }
        }
    }

    private fun handleInnerRecyclerView(it: View, dx: Int, dy: Int, consumed: IntArray, target: View) {
        it.getLocationOnScreen(childLocation)
        // 如果是上下
        if (abs(dx) < abs(dy)) {
            // 如果是向上的
            if (dy >= 0) {
                // ViewPager 当前所处位置没有在顶端，交由父类去滑动
                if (childLocation[1] > (viewPagerStickyHeight + offset)) {
                    consumed[0] = 0
                    consumed[1] = dy
                    scrollBy(0, dy)
                    if (isDebug) {
                        Log.e("dc", "Outer --> onNestedPreScroll 》》消耗向上【dy=$dy】")
                    }
                } else {
                    // 吸顶不允许父类滑动的了，除非子类向下且到头部
                    isLayoutFrozen = true
                    if (isDebug) {
                        Log.e("dc", "Outer --> onNestedPreScroll 》》交由【子类】消耗向上【dy=$dy】")
                    }
                }
            }
            // 如果是向下的
            else {
                if (childLocation[1] > (viewPagerStickyHeight + offset)) {
                    if (!target.canScrollVertically(-1)) {
                        // 如果超过了底部，剩下的滑动全由父类完成
                        if (childLocation[1] >= viewPagerBottomHeight) {

                        }
                        consumed[0] = 0
                        consumed[1] = dy
                        scrollBy(0, dy)
                        if (isDebug) {
                            Log.e("dc", "Outer --> onNestedPreScroll 》》在固定位置消耗向下【dy=$dy】")
                        }
                        isLayoutFrozen = false
                    } else {
                        if (isDebug) {
                            Log.e("dc", "Outer --> onNestedPreScroll 》》在固定位置交由【子类】消耗向下【dy=$dy】")
                        }
                    }
                } else {
                    if (!target.canScrollVertically(-1)) {
                        consumed[0] = 0
                        consumed[1] = dy
                        scrollBy(0, dy)
                        if (isDebug) {
                            Log.e("dc", "Outer --> onNestedPreScroll2 》》不在固定位置消耗向下【dy=$dy】")
                        }
                        isLayoutFrozen = false
                    } else {
                        if (isDebug) {
                            Log.e("dc", "Outer --> onNestedPreScroll2 》》不在固定位置交由【子类】消耗向下【dy=$dy】")
                        }
                    }
                }
            }
        } else {
            // 如果左右滑动一点都不消耗，全部交由 vp 内部
            if (isDebug) {
                Log.e("dc", "Outer --> onNestedPreScroll2 》》一点不消耗左右滑动【dx=$dx】【dy=$dy】")
            }
        }
        if (isDebug) {
            Log.e("dc", "Outer --> onNestedPreScroll 》》【dx=$dx】【dy=$dy】【location[0]=${childLocation[0]}}】" +
                    "【location[1]=${childLocation[1]}】【viewPagerStickyHeight=${viewPagerStickyHeight}】")
        }
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        if(isDebug) {
            Log.e("dc", "Outer --> dispatchNestedPreFling[velocityX=$velocityX][velocityY=$velocityY]")
        }
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        if(isDebug) {
            Log.e("dc", "Outer --> onNestedFling 》》[velocityX=$velocityX][velocityY=$velocityY][consumed=$consumed]")
        }
        mNestedScrollingChildView?.let {
            if (target !is NestedInnerRecyclerView) {
                return@let
            }
            it.getLocationOnScreen(childLocation)
            if (abs(velocityX) < abs(velocityY)){
                if (velocityY < 0 && !target.canScrollVertically(-1)){
                    fling(velocityX.toInt(), velocityY.toInt())
                    return true
                }
            }
        }
        return false
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        if(isDebug) {
            Log.e("dc", "Outer --> onNestedPreFling 》》[velocityX=$velocityX][velocityY=$velocityY]")
        }
        return false
    }
}