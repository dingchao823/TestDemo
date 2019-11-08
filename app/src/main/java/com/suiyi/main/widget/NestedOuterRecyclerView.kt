package com.suiyi.main.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.recyclerview.widget.RecyclerView
import com.suiyi.main.utils.ScreenUtil


class NestedOuterRecyclerView : RecyclerView, NestedScrollingParent2 {

    private val mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private var mNestedScrollingTarget: View? = null
    private var mNestedScrollingChildView : View? = null
    private var maxHeight : Int = -1
    private var childLocation = IntArray(2)
    private val isDebug = false

    init {
        maxHeight = ScreenUtil.getStatusBarHeight() + 100
    }

    constructor(context: Context?) : super(context!!) {}

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle)

    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int, type: Int) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes, type)
        mNestedScrollingTarget = target
        mNestedScrollingChildView = child
        if(isDebug) {
//            Log.e("dc", "Outer --> onNestedScrollAccepted 》》")
        }
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

        mNestedScrollingChildView?.let {
            if (target !is NestedInnerRecyclerView){
                return
            }
            it.getLocationOnScreen(childLocation)
            // 如果是向上的
            if (dy >= 0){
                // ViewPager当前所处位置没有在顶端，交由父类去滑动
                if (childLocation[1] > (maxHeight + 5)) {
                    consumed[0] = 0
                    consumed[1] = dy
                    scrollBy(0, dy)
                }
            }
            // 如果是向下的
            else{
                if (childLocation[1] > (maxHeight + 5)){
                    if (!target.canScrollVertically(-1)){
                        consumed[0] = 0
                        consumed[1] = dy
                        scrollBy(0, dy)
                    }
                }else{
                    if (!target.canScrollVertically(-1)){
                        consumed[0] = 0
                        consumed[1] = dy
                        scrollBy(0, dy)
                    }
                }
            }
            if(isDebug) {
//                Log.e("dc", "Outer --> onNestedPreScroll 》》【dx=$dx】【dy=$dy】【location[0]=${childLocation[0]}}】【location[1]=${childLocation[1]}】【maxHeight=${maxHeight}】")
            }
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        if(isDebug) {
//            Log.e("dc", "Outer --> onStopNestedScroll 》》")
        }
        mNestedScrollingParentHelper.onStopNestedScroll(target, type)
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        if(isDebug) {
//            Log.e("dc", "Outer --> onStartNestedScroll 》》")
        }
        return true
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        if(isDebug) {
//            Log.e("dc", "Outer --> onNestedScroll 》》【dxConsumed=$dxConsumed】【dyConsumed=$dyConsumed】【dxUnconsumed=$dxUnconsumed】【dyUnconsumed=$dyUnconsumed】")
        }
    }
}