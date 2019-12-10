package com.suiyi.main

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.suiyi.main.adapter.EntranceAdapter
import com.example.base.bean.RecyclerBean
import com.suiyi.main.constants.Path
import kotlinx.android.synthetic.main.activity_recycler_view.*

class EntranceActivity : Activity(){

    private lateinit var adapter : EntranceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val manager = GridLayoutManager(this, 4)
        recyclerView_main.layoutManager = manager
        adapter = EntranceAdapter(this)
        adapter.dataSource = ArrayList()
        recyclerView_main.adapter = adapter

        initData()
    }

    private fun initData() {
        adapter.dataSource.add(RecyclerBean("注解", Path.ANNOTATION_ACTIVITY_PATH))
        adapter.dataSource.add(RecyclerBean("kotlin 协程", Path.COROUTINES_SCOPE_ACTIVITY_PATH))
        adapter.dataSource.add(RecyclerBean("仿淘宝二楼", Path.SECOND_FLOOR_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("Scroller学习", Path.SCROLLER_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("Flutter学习", Path.FLUTTER_TEST_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("上RV下指示器", Path.RECYCLER_INDICATOR))
        adapter.dataSource.add(RecyclerBean("Rv一行滑动复位", Path.ONE_SCROLL_RECYCLER_BUG))
        adapter.dataSource.add(RecyclerBean("Lottie 动画", Path.LOTTIE_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("ViewPager嵌套冲突", Path.NESTED_VIEW_PAGER_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("SVGA图片播放", Path.SVGA_PLAYER_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("JetPack组件", Path.JET_PACK_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("LayoutInflater疑问", Path.CONTEXT_TEST_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("RecyclerView 局部刷新实践", Path.RECYCLER_VIEW_DIFF_UTILS))
        adapter.dataSource.add(RecyclerBean("WebP加载", Path.WEBP_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("vLayout 瀑布流闪烁错乱bug", Path.STAGGER_BUG_ACTIVITY))
        adapter.dataSource.add(RecyclerBean("皮肤包下载", Path.DOWNLOAD_SKIN_ACTIVITY))
    }

}
