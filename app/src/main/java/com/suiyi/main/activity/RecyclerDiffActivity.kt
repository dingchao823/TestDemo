package com.suiyi.main.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.suiyi.main.R
import com.suiyi.main.adapter.SimpleTextAdapter
import com.suiyi.main.adapter.SimpleTextWithDiffAdapter
import com.suiyi.main.constants.Path
import kotlinx.android.synthetic.main.activity_recycler_diff.*
import kotlinx.coroutines.*

/**
 * RecyclerView 局部刷新试验
 *
 * 实际并没有什么性能上的特别优化：
 *
 * 1. 只有可见的部分会部分更新，且是设置新值的时候
 * 2. 不可见的部分全部更新，等进入视野之后还是全局更新
 *
 */
@Route(path = Path.RECYCLER_VIEW_DIFF_UTILS)
class RecyclerDiffActivity : Activity(){

    val listA : ArrayList<String> = ArrayList()
    val listB : ArrayList<String> = ArrayList()
    val listC : ArrayList<String> = ArrayList()
    val listD : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_diff)

        for (index in 0..29){
            listA.add("【listA】 : position【$index】")
        }
        for (index in 0..29){
            listB.add("【listB】 : position【$index】")
        }
        for (index in 0..29){
            listC.add("【listC】 : position【$index】")
        }
        for (index in 0..29){
            listD.add("【listD】 : position【$index】")
        }

        val layoutManager = VirtualLayoutManager(this)
        recycler_view_with_diff.layoutManager = layoutManager
        val adapter = DelegateAdapter(layoutManager)
        recycler_view_with_diff.adapter = adapter

        val diffAdapter = SimpleTextWithDiffAdapter()
        adapter.addAdapter(diffAdapter)

        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            var index = 0
            while (true) {
                Log.e("dc", "--------------------------> 第一次设值 index = $index")
                index++
                when(index){
                    1 -> diffAdapter.setDataSource(listA)
                    2 -> diffAdapter.setDataSource(listB)
                    3 -> diffAdapter.setDataSource(listC)
                    4 -> diffAdapter.setDataSource(listD)
                }
                if (index == 4){
                    index = 0
                }
                delay(3000)
            }
        }

    }
}