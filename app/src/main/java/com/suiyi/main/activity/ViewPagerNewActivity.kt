package com.suiyi.main.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.example.base.constants.Path
import com.suiyi.main.R
import com.suiyi.main.adapter.*
import kotlinx.android.synthetic.main.activity_view_pager_new.*

/**
 * 换一种方式实现嵌套 ViewPager
 */
@Route(path = Path.NEW_NESTED_VIEW_PAGER)
class ViewPagerNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_pager_new)

        val virtualLayoutManager = VirtualLayoutManager(this)
        val adapter = DelegateAdapter(virtualLayoutManager)
        recycler_view.layoutManager = VirtualLayoutManager(this)
        recycler_view.adapter = adapter

        for (index in 1..5){
            adapter.addAdapter(SimpleImageAdapter())
            adapter.addAdapter(SimpleDividerAdapter(10))
        }

        adapter.addAdapter(VerticalRecyclerViewAdapter(this))
    }
}
