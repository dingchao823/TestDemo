package com.suiyi.main.activity.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.suiyi.main.R
import com.suiyi.main.adapter.SimpleDividerAdapter
import com.suiyi.main.adapter.SimpleImageAdapter
import com.suiyi.main.adapter.SimpleTextAdapter

class SimpleRecyclerViewFragment : Fragment(){

    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.get("index")?.let {
            index = it as Int
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_nested_view_pager, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_nested)
        val virtualLayoutManager = VirtualLayoutManager(context!!)
        val adapter = DelegateAdapter(virtualLayoutManager)
        recyclerView.layoutManager = VirtualLayoutManager(context!!)
        recyclerView.adapter = adapter
        adapter.addAdapter(SimpleTextAdapter(index))

        Log.e("dc", "【SimpleRecyclerViewFragment ${hashCode()}】 create view")

        return view
    }
}