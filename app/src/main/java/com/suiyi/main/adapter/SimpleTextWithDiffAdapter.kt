package com.suiyi.main.adapter

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.suiyi.main.base.BaseDelegateAdapter.ViewType.SIMPLE_TEXT_WITH_DIFF
import com.suiyi.main.utils.DimenUtils

/**
 * 单张图，预设大小，自适应屏幕宽高
 *
 * @author
 */
class SimpleTextWithDiffAdapter : BaseDelegateAdapter<SimpleTextWithDiffAdapter.ViewHolder, String>(){

    init {
        enableDiffUtils = true
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val singleImage = TextView(context)
        singleImage.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtils.dipTopx( context, 100f))
        singleImage.setTextColor(context!!.resources.getColor(R.color.white))
        singleImage.gravity = Gravity.CENTER
        return ViewHolder(singleImage)
    }

    override fun getViewType(position: Int): Int {
        return SIMPLE_TEXT_WITH_DIFF
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        val helper = LinearLayoutHelper()
        helper.marginLeft = DimenUtils.dipTopx(context, 16f)
        helper.marginRight = DimenUtils.dipTopx(context, 16f)
        helper.setDividerHeight(DimenUtils.dipTopx(context, 3f))
        return helper
    }

    override fun getItemCount(): Int {
        return newDataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(position)
    }

    override fun onBindViewHolderInner(bundle: Bundle, holder: ViewHolder?, position: Int) {
        holder?.onBindViewHolderInner(bundle, holder, position)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val payLoad = Bundle()
        val oldItem = oldDataSource.elementAtOrNull(oldItemPosition)
        val newItem = newDataSource.elementAtOrNull(newItemPosition)
        newItem?.let {
            if (!TextUtils.equals(newItem, oldItem)){
                payLoad.putString("name", newItem)
            }
        }
        if (payLoad.size() == 0){
            return null
        }
        return payLoad
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView as TextView

        fun onBindViewHolder(position: Int) {
            Log.e("dc", "全局刷新【$position】")
            textView.text = newDataSource[position]
            textView.setTextColor(Color.parseColor("#FFFFFF"))
            textView.setBackgroundColor(Color.parseColor("#000000"))
        }

        fun onBindViewHolderInner(bundle: Bundle, holder: ViewHolder?, position: Int) {
            Log.e("dc", "局部刷新【$position】")
            val item = newDataSource.elementAtOrNull(position)
            item?.let { itemInner ->
                bundle.keySet()?.let {
                    for (key in it) {
                        when (key) {
                            "name" -> {
                                textView.text = itemInner
                                textView.setTextColor(Color.parseColor("#000000"))
                                textView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                            }
                        }
                    }
                }
            }
            textView.text = newDataSource[position]
        }
    }

}