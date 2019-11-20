package com.suiyi.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.suiyi.main.R
import com.suiyi.main.utils.DimenUtils
import com.suiyi.main.widget.RecyclerTabLayout

/**
 * 首页 Tab 数据
 *
 * @author 0004640
 */
class SimpleTabAdapter(private val activityContext : Context,
                       viewPager: ViewPager?,
                       private val list : ArrayList<String>)
    : RecyclerTabLayout.Adapter<SimpleTabAdapter.ViewHolder>(viewPager){

    val listener : OnTabSelectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activityContext)
                .inflate(R.layout.item_classify_goods_title, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        list.let {
            holder.title.text = list[position]
            holder.content.text = list[position]
        }

        /** 选中的 **/
        if (position == currentIndicatorPosition){
            holder.title.setTextColor(activityContext.resources.getColor(R.color.color_1BB32F))
            holder.content.setTextColor(activityContext.resources.getColor(R.color.white))
            holder.content.setBackgroundResource(R.drawable.bg_item_classify_goods_activity_green)
        }else{
            holder.title.setTextColor(activityContext.resources.getColor(R.color.color_242424))
            holder.content.setTextColor(activityContext.resources.getColor(R.color.color_8a8a8a))
            holder.content.setBackgroundResource(R.drawable.bg_item_classify_goods_activity_transparent)
        }

    }

    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item){
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val content: TextView = itemView.findViewById(R.id.tvContent)

        init {
            itemView.setOnClickListener{
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    viewPager?.setCurrentItem(adapterPosition, true)
                    listener?.onTabSelect(adapterPosition)
                }
            }
        }
    }

    interface OnTabSelectListener{

        fun onTabSelect(position : Int)

    }

}