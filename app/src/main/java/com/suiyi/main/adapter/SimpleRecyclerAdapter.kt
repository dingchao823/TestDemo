package com.suiyi.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suiyi.main.R
import com.suiyi.main.SHApplication

class SimpleRecyclerAdapter(var context : Context?) : RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_item_scroller, p0, false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, p1: Int) {

        viewHolder.tv_scroller_text.setText("位置为：$p1")

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_scroller_text = itemView.findViewById<TextView>(R.id.tv_scroller_text)

    }

}