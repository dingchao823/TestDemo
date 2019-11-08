package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.suiyi.main.R
import com.suiyi.main.adapter.SimpleOneScrollAdapter
import com.suiyi.main.constants.Path
import kotlinx.android.synthetic.main.activity_one_scroll_recycler_bug.*

/**
 * RecyclerView 中嵌套一行可滑动的 Rv 之后，一行可滑动在复用之后，不管你之前页面滑动了多少都会复位
 *
 * Demo 用以解决复位问题，原因已经知道，是因为Fragment onViewcreated 一直在调用导致一直在设置 Adapter 。
 *
 */
@Route(path = Path.ONE_SCROLL_RECYCLER_BUG)
class RecyclerReuseBugActivity : Activity(){

    private var layoutManager = VirtualLayoutManager(this)

    private var fatherAdapter = DelegateAdapter(layoutManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_one_scroll_recycler_bug)

        recycler_view_one_scroll.layoutManager = layoutManager
        recycler_view_one_scroll.adapter = fatherAdapter

        var adapter : SimpleOneScrollAdapter? = null
        for (index in 1..20) {
            adapter = SimpleOneScrollAdapter()
            adapter.addData("1231231")
            adapter.addData("1231231")
            adapter.addData("1231231")
            adapter.addData("1231231")
            adapter.addData("1231231")
            fatherAdapter.addAdapter(adapter)
        }

    }
}