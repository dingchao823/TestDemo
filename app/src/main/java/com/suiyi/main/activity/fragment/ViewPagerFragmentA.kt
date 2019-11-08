package com.suiyi.main.activity.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.suiyi.main.R
import com.suiyi.main.activity.NestedViewPagerActivity
import com.suiyi.main.adapter.SimpleDividerAdapter
import com.suiyi.main.adapter.SimpleImageAdapter
import com.suiyi.main.adapter.SimpleOneScrollAdapter
import com.suiyi.main.adapter.SimpleViewPagerAdapter
import com.suiyi.main.widget.NestedOuterRecyclerView

/**
 * 主 Fragment
 */
class ViewPagerFragmentA : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_nested_view_pager_activity, null)
        val virtualLayoutManager = VirtualLayoutManager(activity!!)
        val adapter = DelegateAdapter(virtualLayoutManager)
        val recycler_nested = view.findViewById<NestedOuterRecyclerView>(R.id.recycler_nested)
        view.findViewById<Button>(R.id.button).setOnClickListener{
            activity?.let {
                if (it is NestedViewPagerActivity){
                    it.change(2)
                }
            }
        }
        recycler_nested.layoutManager = VirtualLayoutManager(activity!!)
        recycler_nested.adapter = adapter
        for (index in 1..20){
            adapter.addAdapter(SimpleImageAdapter())
            adapter.addAdapter(SimpleDividerAdapter(10))
        }
        adapter.addAdapter(SimpleDividerAdapter(10))
        adapter.addAdapter(SimpleViewPagerAdapter(activity!!.supportFragmentManager))

        Log.e("dc", "【ViewPagerFragmentA】 create view")
        return view
    }

    override fun onDetach() {
        super.onDetach()

        Log.e("dc", "【ViewPagerFragmentA】 detach from activity")
    }
}