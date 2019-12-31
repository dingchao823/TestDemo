package com.suiyi.main.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.suiyi.main.base.BaseDelegateAdapter
import com.suiyi.main.utils.DimenUtils

class SimpleOneScrollAdapterB : BaseDelegateAdapter<SimpleOneScrollAdapterB.ViewHolder, String>(){

    private val adapter = NormalRecyclerAdapter(context)

    override fun getViewType(position: Int): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recyclerView.let {
            if (it.adapter == null){
                it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                it.adapter = adapter
            }
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val recyclerView = RecyclerView(context!!)
        recyclerView.setBackgroundColor(context!!.resources.getColor(android.R.color.holo_orange_dark))
        recyclerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DimenUtils.dipTopx(context, 100f))
        return ViewHolder(recyclerView)
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val recyclerView = itemView as RecyclerView
    }

    override fun getItemCount(): Int {
        return 1
    }
}