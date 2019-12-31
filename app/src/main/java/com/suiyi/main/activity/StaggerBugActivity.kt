package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.suiyi.main.adapter.StaggerGridAdapter
import com.example.base.constants.Path

/**
 * 用 vlayout 实现瀑布流 bug 修复
 *
 * @see StaggerGridLayoutHelper
 *
 */
@Route(path = Path.STAGGER_BUG_ACTIVITY)
class StaggerBugActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = RecyclerView(this)
        setContentView(recyclerView)

        val virtualLayoutManager = VirtualLayoutManager(this)
        val adapter = DelegateAdapter(virtualLayoutManager)
        recyclerView.layoutManager = virtualLayoutManager
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = null

        val staggerAdapter = StaggerGridAdapter(0)
        adapter.addAdapter(staggerAdapter)
        staggerAdapter.notifyDataSetChanged()
    }
}