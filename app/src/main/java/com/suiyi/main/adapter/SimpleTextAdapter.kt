package com.suiyi.main.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.suiyi.main.R
import com.suiyi.main.base.BaseDelegateAdapter
import com.suiyi.main.base.BaseDelegateAdapter.ViewType.HOME_VIEW_TYPE_1_IMAGE
import com.suiyi.main.utils.DimenUtils

/**
 * 单张图，预设大小，自适应屏幕宽高
 *
 * @author
 */
class SimpleTextAdapter(val index : Int = 0) : BaseDelegateAdapter<SimpleTextAdapter.ViewHolder, String>(){

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.textView.text = "当前位置 $index --- $p1"
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val textView = TextView(context)
        textView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtils.dipTopx( context, 100f))
        textView.setTextColor(context!!.resources.getColor(R.color.white))
        textView.gravity = Gravity.CENTER
        textView.setBackgroundColor(Color.parseColor("#000000"))
        return ViewHolder(textView)
    }

    override fun getViewType(position: Int): Int {
        return HOME_VIEW_TYPE_1_IMAGE
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        val helper = LinearLayoutHelper()
        helper.marginLeft = DimenUtils.dipTopx(context, 16f)
        helper.marginRight = DimenUtils.dipTopx(context, 16f)
        return helper
    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView as TextView
    }

}