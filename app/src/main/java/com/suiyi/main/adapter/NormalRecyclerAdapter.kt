package com.suiyi.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suiyi.main.R
import org.w3c.dom.Text

class NormalRecyclerAdapter(var context : Context?) : RecyclerView.Adapter<NormalRecyclerAdapter.ViewHolder>(){



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_normal, p0, false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textView.text = position.toString()

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView as TextView
    }

}