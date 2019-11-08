package com.suiyi.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.suiyi.main.base.BaseDelegateAdapter

/**
 * 分割作用
 *
 * @author 0004640
 */
class SimpleDividerAdapter(var height : Int) : BaseDelegateAdapter<SimpleDividerAdapter.ViewHolder, Any>(){

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = View(context)
        view.layoutParams.let {
            if (it != null){
                it.height = height
                view.layoutParams = it
            }else{
                val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        height)
                view.layoutParams = params
            }
        }
        return ViewHolder(view)
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        return LinearLayoutHelper()
    }

    override fun getViewType(position: Int): Int {
        return 100
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    override fun getItemCount(): Int {
        return 1
    }
}