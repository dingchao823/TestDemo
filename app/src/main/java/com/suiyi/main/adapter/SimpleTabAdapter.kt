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

    private var minTabWidth : Int = DimenUtils.dipTopx(activityContext, 86f)

    private var splitTabWidth : Float = DimenUtils.getDisplayWidth(activityContext) / 4.5f

    var isOnlyShowBottomLine = false

    val listener : OnTabSelectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activityContext)
                .inflate(R.layout.item_classify_goods_title, parent, false)
        // 设置view的宽度和高度，如果屏幕宽度很小，会导致名称换行，因此需要设置一个最小的宽度阈值
        var width: Int = splitTabWidth.toInt()
        if(splitTabWidth <= minTabWidth){
            width = minTabWidth
        }
        view?.layoutParams?.let {
            it.width = width
            it.height = DimenUtils.dipTopx(48f)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        list.let {
            holder.title?.text = "位置$position"
        }

        /** 选中的 **/
        if (position == currentIndicatorPosition){
            holder.title?.setTextColor(activityContext.resources.getColor(R.color.color_1BB32F))
            holder.content?.setTextColor(activityContext.resources.getColor(R.color.white))
            holder.content?.setBackgroundResource(R.drawable.bg_item_classify_goods_green)
            if (isOnlyShowBottomLine){
                holder.content?.visibility = View.INVISIBLE
                holder.bottomLine?.visibility = View.VISIBLE
            }else{
                holder.content?.visibility = View.VISIBLE
                holder.bottomLine?.visibility = View.INVISIBLE
            }
        }else{
            holder.title?.setTextColor(activityContext.resources.getColor(R.color.color_242424))
            holder.content?.setTextColor(activityContext.resources.getColor(R.color.color_8a8a8a))
            holder.content?.setBackgroundResource(R.drawable.bg_item_classify_goods_activity_transparent)
            holder.bottomLine?.visibility = View.INVISIBLE
            if (isOnlyShowBottomLine){
                holder.content?.visibility = View.INVISIBLE
            }else{
                holder.content?.visibility = View.VISIBLE
            }
        }

        // 分割线
        if (position == list.size - 1){
            holder.divider?.visibility = View.INVISIBLE
        }else{
            holder.divider?.visibility = View.VISIBLE
        }

    }

    inner class ViewHolder(item : View) : RecyclerView.ViewHolder(item){
        val title: TextView? = itemView.findViewById(R.id.tvTitle)
        val content: TextView? = itemView.findViewById(R.id.tvContent)
        val divider : View? = itemView.findViewById(R.id.divider)
        val bottomLine : View? = itemView.findViewById(R.id.bottom_line)

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