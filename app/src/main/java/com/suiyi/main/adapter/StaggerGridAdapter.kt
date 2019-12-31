package com.suiyi.main.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.suiyi.main.R
import com.suiyi.main.base.BaseDelegateAdapter
import com.suiyi.main.utils.DimenUtils
import com.suiyi.main.widget.StaggeredGridLayoutHelper
import kotlinx.android.extensions.LayoutContainer

/**
 * @author 0004584
 *
 * 瀑布流适配器测试优化，有多行，有单行
 */
class StaggerGridAdapter(val index : Int) : BaseDelegateAdapter<StaggerGridAdapter.ViewHolder, String>() {

    override fun getViewType(position: Int): Int {
        if (position == itemCount - 1){
            return 2
        }else {
            return 1
        }
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        val helper = StaggeredGridLayoutHelper(2)
        helper.marginLeft = DimenUtils.dipTopx(16f)
        helper.marginRight = DimenUtils.dipTopx(16f)
        helper.marginTop = DimenUtils.dipTopx(16f)
        helper.marginBottom = DimenUtils.dipTopx(16f)
        helper.hGap = DimenUtils.dipTopx(5f)
        helper.vGap = DimenUtils.dipTopx(5f)
        return helper
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            1 -> {
                return ViewHolder(getRootView(parent, R.layout.recycler_item_stagger_grid)!!)
            }
            2 -> {
                val view = getRootView(parent, R.layout.recycler_item_stagger_grid)!!
                view.layoutParams?.let {
                    if (it is StaggeredGridLayoutManager.LayoutParams){
                        it.isFullSpan = true
                    }
                }
                return ViewHolder(view)
            }
        }
        return ViewHolder(getRootView(parent, R.layout.recycler_item_stagger_grid)!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(position)
    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        val ad = containerView.findViewById<TextView>(R.id.iv_ad)

        fun onBindViewHolder(position: Int) {
            newDataSource.elementAtOrNull(position)
            debugCode(position)
            containerView.setOnClickListener {
                Toast.makeText(context, "点击了$position", Toast.LENGTH_SHORT).show()
            }
        }

        private fun debugCode(position: Int) {
            ad.text = "$position"
            when(position % 6){
                0 -> {
                    setImageHeight(600)
                }
                1 -> {
                    setImageHeight(200)
                }
                2 -> {
                    setImageHeight(400)
                }
                3 -> {
                    setImageHeight(300)
                }
                4 -> {
                    setImageHeight(100)
                }
                5 -> {
                    setImageHeight(700)
                }
            }
        }

        private fun setImageHeight(heightPx : Int){
            val params = ad.layoutParams as ConstraintLayout.LayoutParams
            params.height = heightPx
            ad.layoutParams = params
        }


    }
}