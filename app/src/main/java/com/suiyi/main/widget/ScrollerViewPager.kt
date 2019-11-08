package com.suiyi.main.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.Scroller
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.absoluteValue


/**
 * 自定义 ViewPager
 *
 * @author dingchao
 */
class ScrollerViewPager(context: Context?, attributeSet: AttributeSet): ViewGroup(context, attributeSet), NestedScrollingParent2 {

    private var mLastX = 0
    private var mLastY = 0
    private var mScroller: Scroller? = null
    private var mVelocityTracker: VelocityTracker? = null
    private var mTouchSlop = 0
    private var mMaxVelocity = 0
    private var mCurrentPage = 0
    /** 是否是往下拉 **/
    private var mShowTop = false
    /** 是否是往上滑 **/
    private var mHideTop = false

    init {
        init(context)
        isNestedScrollingEnabled = true
    }

    private fun init(context: Context?) {
        mScroller = Scroller(context)
        val config: ViewConfiguration = ViewConfiguration.get(context)
        mTouchSlop = config.scaledPagingTouchSlop
        mMaxVelocity = config.scaledMinimumFlingVelocity
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        var top = - getChildAt(0).measuredHeight
        var bottom = 0

        // header
        var child: View = getChildAt(0)
        child.layout(l, - getChildAt(0).measuredHeight, r, 0)

        // recyclerView
        child = getChildAt(1)
        Log.e("dc", "recyclerView height : ${child.measuredHeight}")
        child.layout(l, 0, r, measuredHeight)
        child.let {
            if (it is RecyclerView){

            }
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        initVelocityTrackerIfNotExists()
        mVelocityTracker!!.addMovement(ev)
        val x = ev.x.toInt()
        val y = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller!!.isFinished) {
                    mScroller!!.abortAnimation()
                }
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = mLastX - x
                val dy = mLastY - y
                mLastX = x
                mLastY = y
                if (scrollY <= -300){
                    return true
                }
                scrollBy(0, dy)
            }
            MotionEvent.ACTION_UP -> {
//                mVelocityTracker!!.computeCurrentVelocity(1000)
//                val initVelocity = mVelocityTracker?.yVelocity?.toInt() ?: 0
//                if (initVelocity > mMaxVelocity && mCurrentPage > 0) {
//                    scrollToPage(mCurrentPage - 1)
//                } else if (initVelocity < -mMaxVelocity && mCurrentPage < childCount - 1) {
//                    scrollToPage(mCurrentPage + 1)
//                } else {
//                    slowScrollToPage()
//                }
                if (scrollY <= 0){
                    mScroller?.startScroll(0, scrollY, 0, -scrollY, Math.abs(mLastY) * 2)
                    invalidate()
                }
                recycleVelocityTracker()
            }
        }
        return true
    }

    /**
     * 缓慢滑动抬起手指的情形，需要判断是停留在本Page还是往前、往后滑动
     */
    private fun slowScrollToPage() {
        val whichPage = (scrollX + width / 2) / width
        scrollToPage(whichPage)
    }

    /**
     * 滑动到指定屏幕
     * @param indexPage
     */
    private fun scrollToPage(indexPage: Int) {
        mCurrentPage = indexPage
        if (mCurrentPage > childCount - 1) {
            mCurrentPage = childCount - 1
        }
        //计算滑动到指定Page还需要滑动的距离
        val dx = mCurrentPage * width - scrollX
        mScroller!!.startScroll(scrollX, 0, dx, 0, Math.abs(dx) * 2)//动画时间设置为Math.abs(dx) * 2 ms

        //记住，使用Scroller类需要手动invalidate
        invalidate()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller!!.computeScrollOffset()) {
            scrollTo(mScroller!!.currX, mScroller!!.currY)
            invalidate()
        }
    }

    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.e("dc", "【father】 onStopNestedScroll")
        var yMe = dy
        mShowTop = yMe < 0 && scrollY.absoluteValue < 200 && !target.canScrollVertically(-1)//往下拉
        if (mShowTop) {
            if ((scrollY + yMe).absoluteValue > 200) {//如果超过了指定位置
                yMe = -(200 - scrollY.absoluteValue)//滑动到指定位置
            }
        }
        mHideTop = yMe > 0 && scaleY < 0//往上滑
        if (mHideTop) {
            if (yMe + scrollY > 0) {//如果超过了初始位置
                yMe = -scrollY//滑动到初始位置
            }
        }
        if (mShowTop || mHideTop) {
            consumed[1] = yMe//消耗纵向距离
            scrollBy(0, yMe)//外部View滚动
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        Log.e("dc", "【father】 onStopNestedScroll")
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        Log.e("dc", "【father】 onStartNestedScroll")
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL !== 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.e("dc", "【father】 onNestedScrollAccepted")
    }

    override fun onNestedScroll(child: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        Log.e("dc", "【father】 onNestedScroll： dxConsumed【$dxConsumed】; dyConsumed【$dyConsumed】; dxUnconsumed【$dxUnconsumed】; dyUnconsumed【$dyUnconsumed】;")
        if (dyConsumed < 0){
            scrollBy(0, dyUnconsumed)
        }
    }
}