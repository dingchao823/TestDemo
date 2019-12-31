package com.gem.tastyfood.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.Interpolator
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.recyclerview.widget.RecyclerView
import com.suiyi.main.activity.NestedViewPagerActivity
import com.suiyi.main.activity.fragment.ViewPagerFragmentA
import com.suiyi.main.utils.FlingUtils
import com.suiyi.main.utils.TabAnimationUtil
import com.suiyi.main.widget.OverScroller
import org.greenrobot.eventbus.EventBus
import kotlin.math.abs

/**
 * 处理嵌套滑动，外层
 *
 * isLayoutFrozen 不要盲目使用，会导致 UI 不刷新等问题
 *
 * @author 0004640
 */
class NestedOuterRecyclerView : RecyclerView, NestedScrollingParent2 {

    private val mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private var mNestedScrollingTarget: View? = null
    private var mNestedScrollingChildView: View? = null
    /** ViewPager 固定的高度  */
    var viewPagerStickyHeight = -1
    /** 误差范围 **/
    private val offset = 10
    private val childLocation = IntArray(2)
    private var downX : Float = 0f
    private var downY : Float = 0f
    private var isDebug = false

    private var flingUtils = FlingUtils(context)
    private var tabAnimationUtil = TabAnimationUtil(context)
    private val listenerList = ArrayList<NestedListener?>()

    private var nestedState = NESTED_NOT_STICKY
    private var isIntercept = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        ViewPagerFragmentA.outerRecyclerView = this
        addNestedListener(tabAnimationUtil)
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        val x = e?.x
        val y = e?.y
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x ?: 0f
                downY = y ?: 0f
                parent.requestDisallowInterceptTouchEvent(true)
                printLog("ACTION_DOWN")
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
                printLog("ACTION_MOVE")
            }
        }

        return if (isIntercept){
            val Intercept = super.onInterceptTouchEvent(e)
            printLog("onInterceptTouchEvent Intercept=$Intercept")
            Intercept
        }else{
            printLog("onInterceptTouchEvent false 不拦截")
            false
        }
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
        printLog("onStartNestedScroll")
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
        mNestedScrollingTarget = target
        mNestedScrollingChildView = child
        printLog("onNestedScrollAccepted")
//        printLog("onNestedScrollAccepted >>>> mNestedScrollingTarget = $mNestedScrollingTarget")
//        printLog("onNestedScrollAccepted>>>> mNestedScrollingChildView = $mNestedScrollingChildView")
        if (target is NestedInnerRecyclerView){
            flingUtils.attach(innerRecyclerView = target)
            tabAnimationUtil.attch(viewPager = ViewPagerFragmentA.viewPager)
            tabAnimationUtil.attch(tabLayoutParam = ViewPagerFragmentA.tabLayout)
            tabAnimationUtil.attch(recyclerView = target)
            isIntercept = false
        }else{
            isIntercept = true
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        printLog("onStopNestedScroll")
        mNestedScrollingParentHelper.onStopNestedScroll(target, type)
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
//        if (isDebug) {
//            Log.e("dc", "Outer --> onNestedScroll 》》【dxConsumed=$dxConsumed】【dyConsumed=$dyConsumed】【dxUnconsumed=$dxUnconsumed】【dyUnconsumed=$dyUnconsumed】")
//        }
        printLog("onNestedScroll")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        printLog("onNestedPreScroll")
        mNestedScrollingChildView?.let {
            if (target !is NestedInnerRecyclerView) {
                return
            }
            it.getLocationOnScreen(childLocation)
            // 如果是上下
            if(abs(dx) < abs(dy)){
                // 如果是向上的
                if (dy >= 0) {
                    // ViewPager 当前所处位置没有在顶端，交由父类去滑动
                    if (childLocation[1] > (viewPagerStickyHeight + offset)) {
                        consumed[0] = 0
                        consumed[1] = dy
                        scrollBy(0, dy)
//                        printLog("onNestedPreScroll 》》消耗向上【dy=$dy】")
                        notifyListener(NESTED_NOT_STICKY)
                    }else{
                        // 如果吸顶了，用户无法通过滑动 tab 切来滑动，整个父布局要固定死，直到用户滑动商品
                        // 瀑布流到指定位置，才可以下拉；在恰当的时候要去解开外布局的滑动
//                        printLog("onNestedPreScroll 》》交由【子类】消耗向上【dy=$dy】")
                        notifyListener(NESTED_STICKY)
                    }
                }
                // 如果是向下的
                else {
                    if (childLocation[1] > (viewPagerStickyHeight + offset)) {
                        if (!target.canScrollVertically(-1)) {
                            consumed[0] = 0
                            consumed[1] = dy
                            scrollBy(0, dy)
//                            printLog("onNestedPreScroll 》》在固定位置消耗向下【dy=$dy】")
                            notifyListener(NESTED_NOT_STICKY)
                        }else{
//                            printLog("onNestedPreScroll 》》在固定位置交由【子类】消耗向下【dy=$dy】")
                            notifyListener(NESTED_STICKY)
                        }
                    } else {
                        if (!target.canScrollVertically(-1)) {
                            consumed[0] = 0
                            consumed[1] = dy
                            scrollBy(0, dy)
//                            printLog("onNestedPreScroll2 》》不在固定位置消耗向下【dy=$dy】")
                            notifyListener(NESTED_NOT_STICKY)
                        }else{
//                            printLog("onNestedPreScroll2 》》不在固定位置交由【子类】消耗向下【dy=$dy】")
                            notifyListener(NESTED_STICKY)
                        }
                    }
                }
            }
            else{
                // 如果左右滑动一点都不消耗，全部交由 vp 内部
                printLog("onNestedPreScroll2 》》不消耗左右滑动【dx=$dx】【dy=$dy】")
            }
//            if(isDebug) {
//                Log.e("dc", "Outer --> onNestedPreScroll 》》【dx=$dx】【dy=$dy】【location[0]=${childLocation[0]}}】" +
//                        "【location[1]=${childLocation[1]}】【viewPagerStickyHeight=${viewPagerStickyHeight}】")
//            }
        }
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        printLog("dispatchNestedPreFling[velocityX=$velocityX][velocityY=$velocityY]")
        flingUtils.outerNeedFlingVelocity = velocityY.toInt()
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        printLog("onNestedFling 》》[velocityX=$velocityX][velocityY=$velocityY][consumed=$consumed]")
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
        printLog("onNestedPreFling 》》[velocityX=$velocityX][velocityY=$velocityY]")
        flingUtils.innerNeedFillingVelocity = velocityY.toInt()
        return false
    }

    private fun printLog(content : String) {
        if (isDebug) {
            Log.e("dc", "Outer --> $content")
        }
    }

    fun addNestedListener(listener: NestedListener?){
        listener?.let {
            listenerList.add(listener)
        }
    }

    fun removeNestedListener(listener: NestedListener?){
        listener?.let {
            listenerList.remove(listener)
        }
    }

    fun notifyListener(newState : Int){
        if (nestedState != newState){
            when(newState){
                NESTED_STICKY -> {
                    for (listen in listenerList){
                        listen?.onNestedTargetSticky()
                    }
                }
                NESTED_NOT_STICKY -> {
                    for (listen in listenerList){
                        listen?.onNestedTargetNotSticky()
                    }
                }
            }
        }
        nestedState = newState
    }

    interface NestedListener {

        fun onNestedTargetNotSticky()

        fun onNestedTargetSticky()

    }

    companion object{

        const val IDLE = 0
        const val NESTED_STICKY = 1
        const val NESTED_NOT_STICKY = 2

    }
}