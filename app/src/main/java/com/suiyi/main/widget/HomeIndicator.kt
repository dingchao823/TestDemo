package com.gem.tastyfood.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.suiyi.main.R
import com.suiyi.main.utils.DimenUtils

/**
 * 首页两行可滑动指示器
 *
 * @author 0004640
 */
class HomeIndicator(context: Context, attrs : AttributeSet) : FrameLayout(context, attrs){

    private var recyclerView : RecyclerView? = null

    private var scrollInstance : Int = 0

    init {
        setBackgroundResource(R.drawable.bg_home_scroll_2_row_progress_bar)

        val slider = ImageView(context)
        slider.layoutParams = LayoutParams(DimenUtils.dipTopx(context, 16f), ViewGroup.LayoutParams.MATCH_PARENT)
        slider.setImageResource(R.mipmap.ic_home_scroll_2_row_indicator)

        removeAllViews()
        addView(slider)
    }

    fun attachToRecyclerView(recyclerView : RecyclerView?, recyclerViewWidth : Int){
        scrollInstance = 0
        this.recyclerView = recyclerView
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                scrollInstance += dx

                Log.e("dc", "scrollInstance = $scrollInstance")
                Log.e("dc", "recyclerViewWidth = ${recyclerViewWidth.toFloat() - DimenUtils.getDisplayWidth(context)}")

                var ratio = scrollInstance.toFloat() / (recyclerViewWidth.toFloat() - DimenUtils.getDisplayWidth(context))
                if (ratio <= 0){
                    ratio = 0f
                }
                if (ratio >= 1){
                    ratio = 1f
                }

                Log.e("dc", "ratio = $ratio")

                getIndicatorView().let {
                    val params = it?.layoutParams
                    if(params is FrameLayout.LayoutParams){
                        params.leftMargin = DimenUtils.dipTopx(context, ratio * 16f)
                    }
                    it?.layoutParams = params
                    it?.requestLayout()
                }

            }
        })
    }

    fun getIndicatorView() : View?{
        return getChildAt(0)
    }

}