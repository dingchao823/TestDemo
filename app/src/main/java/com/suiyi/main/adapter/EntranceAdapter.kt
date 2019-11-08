package com.suiyi.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.alibaba.android.arouter.launcher.ARouter
import com.suiyi.main.R
import com.example.base.bean.RecyclerBean

class EntranceAdapter(var context : Context) : RecyclerView.Adapter<EntranceAdapter.ViewHolder>(){

    var dataSource : ArrayList<RecyclerBean> = ArrayList()

    set(value) {
        val diffResult : DiffUtil.DiffResult? = DiffUtil.calculateDiff(RecyclerViewDiffUtils(field, value), true)
        diffResult?.dispatchUpdatesTo(this)

        field = value
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_view, p0, false))
    }

    override fun getItemCount(): Int = (dataSource?.size) ?: 0

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {

        holder.positionView?.text = dataSource?.get(position)?.name

        holder.itemView.setOnClickListener{
            ARouter.getInstance().build(dataSource[position].routerUrl).navigation()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val positionView : TextView? = itemView.findViewById(R.id.position)

    }

}