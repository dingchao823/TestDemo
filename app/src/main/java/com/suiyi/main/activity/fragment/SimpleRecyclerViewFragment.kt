package com.suiyi.main.activity.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.gem.tastyfood.widget.NestedInnerRecyclerView
import com.suiyi.main.R
import com.suiyi.main.adapter.*
import com.suiyi.main.widget.StaggeredDividerItemDecoration

class SimpleRecyclerViewFragment : Fragment(){

    var index = -1

    /** 界面是否已创建完成  */
    private var isViewCreated = false
    /** 是否对用户可见  */
    private var isVisibleToUser = false
    /** 更新数据使用  */
    private var isFirstEnter = true
    /** 数据是否加载过  */
    private var isDataInitiated = false
    private var innerRecyclerView : NestedInnerRecyclerView? = null

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        resume()
        Log.e("dc", "【SimpleRecyclerViewFragment ${hashCode()}-${index}】 setUserVisibleHint=$isVisibleToUser")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreated = true
        resume()
        Log.e("dc", "【SimpleRecyclerViewFragment ${hashCode()}-${index}】onActivityCreated")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.get("index")?.let {
            index = it as Int
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("dc", "【SimpleRecyclerViewFragment ${hashCode()}-${index}】 onResume")
        resume()
    }

    private fun resume() {
        if (isViewCreated && isVisibleToUser) {
            innerRecyclerView?.let {
                ViewPagerFragmentA.innerRecyclerView = it
                Log.e("dc", "【SimpleRecyclerViewFragment ${hashCode()}-${index}】 变更为 ${it.hashCode()}")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nested_view_pager, null)
        innerRecyclerView = view.findViewById<NestedInnerRecyclerView>(R.id.recycler_nested)
        val virtualLayoutManager = VirtualLayoutManager(context!!)
        val adapter = DelegateAdapter(virtualLayoutManager)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        innerRecyclerView?.layoutManager = manager
        innerRecyclerView?.adapter = StaggerGridAdapter(index)
        innerRecyclerView?.itemAnimator = null
        innerRecyclerView?.addItemDecoration(StaggeredDividerItemDecoration(context!!, 5))

        adapter.addAdapter(StaggerGridAdapter(index))

        Log.e("dc", "【SimpleRecyclerViewFragment ${hashCode()}-${index}】 create view")

        return view
    }
}