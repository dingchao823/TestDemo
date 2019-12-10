package com.suiyi.main.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suiyi.main.R
import com.example.base.bean.PagingBean
import com.suiyi.main.utils.DimenUtils

class PagingAdapter(val context: Context): PagedListAdapter<PagingBean, PagingAdapter.ViewHolder>(MyItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = TextView(context)
        textView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtils.dipTopx( context, 100f))
        textView.setTextColor(context.resources.getColor(R.color.white))
        textView.gravity = Gravity.CENTER
        textView.setBackgroundColor(Color.parseColor("#000000"))
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.e("dc", "onBindViewHolder")

        // 必须调用 getItem，不然不触发 load after
        holder.textView.text = getItem(position)?.name
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView as TextView
    }

    class MyItemCallBack : DiffUtil.ItemCallback<PagingBean>(){

        override fun areItemsTheSame(oldItem: PagingBean, newItem: PagingBean): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PagingBean, newItem: PagingBean): Boolean {
            return oldItem == newItem
        }

    }

}