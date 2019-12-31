package com.suiyi.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.suiyi.main.R
import com.suiyi.main.base.BaseDelegateAdapter
import com.suiyi.main.widget.StaggeredDividerItemDecoration

class VerticalRecyclerViewAdapter(val activityContext : Context) : BaseDelegateAdapter<VerticalRecyclerViewAdapter.ViewHolder, Any>(){


    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }

    override fun getViewType(position: Int): Int {
        return 233
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerView(activityContext)
        view.layoutParams?.let {
            it.width = ViewGroup.LayoutParams.MATCH_PARENT
            it.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        val virtualLayoutManager = VirtualLayoutManager(activityContext)
        val adapter = DelegateAdapter(virtualLayoutManager)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        view.layoutManager = manager
        view.adapter = StaggerGridAdapter(0)
        view.itemAnimator = null
        view.addItemDecoration(StaggeredDividerItemDecoration(activityContext, 5))
        adapter.addAdapter(StaggerGridAdapter(0))
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    }

}