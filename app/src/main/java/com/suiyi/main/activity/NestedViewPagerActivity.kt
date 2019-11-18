package com.suiyi.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.suiyi.main.R
import com.suiyi.main.activity.fragment.ViewPagerFragmentA
import com.suiyi.main.activity.fragment.ViewPagerFragmentB
import com.suiyi.main.adapter.SimpleDividerAdapter
import com.suiyi.main.adapter.SimpleImageAdapter
import com.suiyi.main.adapter.SimpleOneScrollAdapter
import com.suiyi.main.adapter.SimpleViewPagerAdapter
import com.suiyi.main.constants.Path.Companion.NESTED_VIEW_PAGER_ACTIVITY
import kotlinx.android.synthetic.main.fragment_nested_view_pager_activity.*
import org.greenrobot.eventbus.EventBus

/**
 * RecyclerView + ViewPager + RecyclerView 嵌套滑动冲突问题解决方案
 *
 * 同时对 ViewPager 进行切换的时候，会导致的Fragment 崩溃
 */
@Route(path = NESTED_VIEW_PAGER_ACTIVITY)
class NestedViewPagerActivity : AppCompatActivity(){

    private val A = ViewPagerFragmentA()
    private val B = ViewPagerFragmentB()

    override fun onStart() {
        super.onStart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nested_view_pager)

        change(1)
    }

    fun change(type : Int){
        supportFragmentManager.beginTransaction().replace(R.id.content, type.let {
            when(type){
                1 -> A
                2 -> B
                else -> A
            }
        }).commitAllowingStateLoss()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}