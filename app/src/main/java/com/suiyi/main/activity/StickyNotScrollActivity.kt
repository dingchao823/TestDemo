package com.suiyi.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.suiyi.main.R
import com.suiyi.main.adapter.*
import com.example.base.constants.Path
import com.suiyi.main.utils.DimenUtils
import com.suiyi.main.utils.ScreenUtil
import kotlinx.android.synthetic.main.activity_sticky_not_scroll.*

/**
 * 当RecyclerView A 内部嵌套横向可滑动 RecyclerView B 时，要求当 B 处于吸顶的情况下，点击 B 父类也无法上下滑动
 *
 */
@Route(path = Path.STICKY_NO_SCROLL_ACTIVITY)
class StickyNotScrollActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sticky_not_scroll)

        val virtualLayoutManager = VirtualLayoutManager(this)
        val adapter = DelegateAdapter(virtualLayoutManager)
        recycler_nested.viewPagerStickyHeight = ScreenUtil.getStatusBarHeight()
        recycler_nested.layoutManager = virtualLayoutManager
        recycler_nested.adapter = adapter
        for (index in 1..5){
            adapter.addAdapter(SimpleDividerAdapter(10))
            adapter.addAdapter(SimpleImageAdapter())
        }
        adapter.addAdapter(SimpleDividerAdapter(10))
        adapter.addAdapter(SimpleViewPagerAdapter(supportFragmentManager, this))
    }
}
