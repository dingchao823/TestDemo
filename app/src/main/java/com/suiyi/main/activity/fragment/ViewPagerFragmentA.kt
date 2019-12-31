package com.suiyi.main.activity.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.gem.tastyfood.widget.NestedInnerRecyclerView
import com.gem.tastyfood.widget.NestedOuterRecyclerView
import com.suiyi.main.R
import com.suiyi.main.activity.NestedViewPagerActivity
import com.suiyi.main.adapter.SimpleDividerAdapter
import com.suiyi.main.adapter.SimpleImageAdapter
import com.suiyi.main.adapter.SimpleOneScrollAdapter
import com.suiyi.main.adapter.SimpleViewPagerAdapter
import com.suiyi.main.event.FlingEvent
import com.suiyi.main.utils.DimenUtils
import com.suiyi.main.utils.ScreenUtil
import com.suiyi.main.widget.RecyclerTabLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * 主 Fragment
 */
class ViewPagerFragmentA : Fragment(){

    private lateinit var recyclerNested : NestedOuterRecyclerView

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_nested_view_pager_activity, null)
        val virtualLayoutManager = VirtualLayoutManager(activity!!)
        val adapter = DelegateAdapter(virtualLayoutManager)
        recyclerNested = view.findViewById(R.id.recycler_nested)
        recyclerNested.viewPagerStickyHeight = ScreenUtil.getStatusBarHeight()
        recyclerNested.layoutManager = VirtualLayoutManager(activity!!)
        recyclerNested.adapter = adapter
        for (index in 1..5){
            adapter.addAdapter(SimpleImageAdapter())
            adapter.addAdapter(SimpleDividerAdapter(10))
        }
        adapter.addAdapter(SimpleOneScrollAdapter())
        adapter.addAdapter(SimpleDividerAdapter(10))
        adapter.addAdapter(SimpleViewPagerAdapter(activity!!.supportFragmentManager, activity as Activity))

        return view
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFlingEvent(event: FlingEvent?) {
        event?.let {
            recyclerNested.fling(it.flingX, it.flingY)
        }
    }

    override fun onDetach() {
        super.onDetach()

        Log.e("dc", "【ViewPagerFragmentA】 detach from activity")
    }

    companion object{
        var outerRecyclerView : NestedOuterRecyclerView? = null
        var tabLayout : RecyclerTabLayout? = null
        var viewPager : ViewPager? = null
        var innerRecyclerView : NestedInnerRecyclerView? = null
    }
}