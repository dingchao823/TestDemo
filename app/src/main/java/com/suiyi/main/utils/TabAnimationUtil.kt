package com.suiyi.main.utils

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.gem.tastyfood.widget.NestedInnerRecyclerView
import com.gem.tastyfood.widget.NestedOuterRecyclerView
import com.suiyi.main.adapter.SimpleTabAdapter
import com.suiyi.main.adapter.SimpleViewPagerAdapter
import com.suiyi.main.widget.RecyclerTabLayout
import kotlin.math.abs

class TabAnimationUtil(val context : Context) : NestedOuterRecyclerView.NestedListener {

    @Suppress("PrivatePropertyName")
    private val DEBUG = false
    private var tabLayout : RecyclerTabLayout? = null
    private var tabAdapter : SimpleTabAdapter? = null
    private var recyclerView : NestedInnerRecyclerView? = null
    private var viewPager : ViewPager? = null
    private val scrollListener = TabAnimationScrollListener()
    private var scrollState = 0

    private val MAX_TAB_HEIGHT = DimenUtils.dipTopx(48f)
    private val MIN_TAB_HEIGHT = DimenUtils.dipTopx(40f)
    private val MIN_SCROLL_RESPONSE = 30
    private val range = MAX_TAB_HEIGHT - MIN_TAB_HEIGHT
    private var MIN_VP_HEIGHT = -1
    private var MAX_VP_HEIGHT = -1
    private var normalViewPagerHeight = -1
    private var currentViewPagerHeight = 0
    private var currentTabHeight = 0
    private var scrollSumList : HashMap<Int, Int?> = HashMap(15)

    private var scrollSum = 0

    fun attch(tabLayoutParam : RecyclerTabLayout? = null,
              recyclerView : NestedInnerRecyclerView? = null, viewPager : ViewPager? = null){
        tabLayoutParam?.let {
            if (tabLayout != it){
                tabLayout = tabLayoutParam
            }
        }
        tabLayoutParam?.adapter?.let {
            if (it != this.tabAdapter && it is SimpleTabAdapter){
                this.tabAdapter = it
                print("TabAnimationUtil -> attach to tab adapter")
            }
        }
        recyclerView?.let {
            if (it != this.recyclerView){
                this.recyclerView?.removeOnScrollListener(scrollListener)
                this.recyclerView = it
                it.removeOnScrollListener(scrollListener)
//                it.addOnScrollListener(scrollListener)
            }
        }
        viewPager?.let {
            if (it != this.viewPager){
                this.viewPager = it
                normalViewPagerHeight = it.layoutParams?.height ?: -1
                MIN_VP_HEIGHT = normalViewPagerHeight
                MAX_VP_HEIGHT = normalViewPagerHeight + range
            }
        }
    }

    override fun onNestedTargetNotSticky() {
        resetTabHeight(MAX_TAB_HEIGHT)
        resetViewPagerHeight(MIN_VP_HEIGHT)
        tabAdapter?.isOnlyShowBottomLine = false
        tabAdapter?.notifyDataSetChanged()
    }

    override fun onNestedTargetSticky() {
        resetTabHeight(MIN_TAB_HEIGHT)
        resetViewPagerHeight(MAX_VP_HEIGHT)
        tabAdapter?.isOnlyShowBottomLine = true
        tabAdapter?.notifyDataSetChanged()
    }

    private inner class TabAnimationScrollListener : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            scrollSum += dy
            print("TabAnimationScrollListener >>> onScrolled >>>> scrollSum = $scrollSum, scrollState=$scrollState")
            // 向上滑动
            if (dy > 0){
                if (scrollSum  > MIN_SCROLL_RESPONSE + 15){
                    if (scrollState != 1) {
                        resetTabHeight(MIN_TAB_HEIGHT)
                        resetViewPagerHeight(MAX_VP_HEIGHT)
                        tabAdapter?.isOnlyShowBottomLine = true
                        tabAdapter?.notifyDataSetChanged()
                        print("TabAnimationScrollListener >>> onScrolled >>>> 变短")
                    }
                    scrollState = 1
                }
            }
            // 向下滑动
            else{
                if (scrollSum  <= MIN_SCROLL_RESPONSE - 15) {
                    if (scrollState != 0) {
                        resetTabHeight(MAX_TAB_HEIGHT)
                        resetViewPagerHeight(MIN_VP_HEIGHT)
                        tabAdapter?.isOnlyShowBottomLine = false
                        tabAdapter?.notifyDataSetChanged()
                        print("TabAnimationScrollListener >>> onScrolled >>>> 变长")
                    }
                    scrollState = 0
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

        }
    }

    private data class Param(var scrollSum : Int, var scrollState : Int)

    private fun resetTabHeight(height : Int){
        tabLayout?.layoutParams?.let {
            it.height = height
        }
        tabLayout?.requestLayout()
    }

    private fun resetViewPagerHeight(height : Int){
        viewPager?.layoutParams?.let {
            it.height = height
        }
        viewPager?.requestLayout()
    }

    private fun print(content : String){
        if (DEBUG){
            Log.e("dc", content)
        }
    }

}