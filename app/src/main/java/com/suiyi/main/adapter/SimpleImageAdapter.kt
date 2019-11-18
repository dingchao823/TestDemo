package com.suiyi.main.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.alibaba.android.vlayout.layout.StickyLayoutHelper
import com.suiyi.main.R
import com.suiyi.main.base.BaseDelegateAdapter
import com.suiyi.main.base.BaseDelegateAdapter.ViewType.HOME_VIEW_TYPE_1_IMAGE
import com.suiyi.main.base.BaseDelegateAdapter.ViewType.HOME_VIEW_TYPE_FLASH_SALE
import com.suiyi.main.utils.DimenUtils
import java.util.*

/**
 * 单张图，预设大小，自适应屏幕宽高
 *
 * @author
 */
class SimpleImageAdapter(var isSticky : Boolean = false, var resId : Int = R.mipmap.comvip_list_banner) :
        BaseDelegateAdapter<SimpleImageAdapter.ViewHolder, String>(){

    companion object{
        val images : Array<Int> = arrayOf(R.mipmap.image_1,
                R.mipmap.image_2,
                R.mipmap.image_3,
                R.mipmap.image_4,
                R.mipmap.image_5,
                R.mipmap.comvip_list_banner)
    }


    var random : Random = Random()

    /** 图片宽度 **/
    var imageHeight : Int = 258
    /** 图片高度 **/
    var imageWidth : Int = 686

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val singleImage = ImageView(context)
        singleImage.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtils.dipTopx( context, 300f))
        singleImage.scaleType = ImageView.ScaleType.FIT_XY
        singleImage.setImageResource(resId)
        singleImage.setBackgroundResource(R.color.colorPrimary)
        return ViewHolder(singleImage)
    }

    override fun getViewType(position: Int): Int {
        if (isSticky){
            return HOME_VIEW_TYPE_FLASH_SALE
        }else {
            return HOME_VIEW_TYPE_1_IMAGE
        }
    }

    override fun onCreateLayoutHelper(): LayoutHelper {
        if (isSticky){
            val helper = StickyLayoutHelper()
            helper.marginLeft = DimenUtils.dipTopx(context, 16f)
            helper.marginRight = DimenUtils.dipTopx(context, 16f)
            return helper
        }else {
            val helper = LinearLayoutHelper()
            helper.marginLeft = DimenUtils.dipTopx(context, 16f)
            helper.marginRight = DimenUtils.dipTopx(context, 16f)
            return helper
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

}