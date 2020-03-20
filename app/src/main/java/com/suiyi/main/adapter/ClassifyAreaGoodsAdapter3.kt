package com.suiyi.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.orhanobut.logger.Logger
import com.suiyi.main.R
import com.suiyi.main.SHApplication
import com.suiyi.main.bean.PoolBean
import com.suiyi.main.constants.POOL_TYPE
import com.suiyi.main.utils.GoodsLoadUtil
import kotlinx.android.extensions.LayoutContainer
import kotlin.collections.ArrayList

/**
 * 瀑布类适配器
 *
 * 最优解！！！
 */
class ClassifyAreaGoodsAdapter3(val activityContext: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /** 当前适配器状态 **/
    var adapterStatus = ADAPTER_STATUS_LOADING
    var preAdapterStatus = adapterStatus
    /** 数据源 **/
    var newDataSource : ArrayList<PoolBean> = ArrayList()
    /** 为预加载数据服务 **/
    var rangeArray = arrayOf(-1, -1)
    /** 数据加载器 **/
    var loadUtil : GoodsLoadUtil = GoodsLoadUtil()

    /** 状态变化通知 **/
    private val statusChangingObserver = Observer<Int> {
        preAdapterStatus = adapterStatus
        adapterStatus = it
        if (itemCount > 0){
            // 如果完成了，刷新最后一个 item
            if (adapterStatus == ADAPTER_STATUS_LOADING_COMPLETE){
                notifyItemChanged(itemCount - 1)
                Log.e("dc", "statusChangingObserver >>>> ADAPTER_STATUS_LOADING_COMPLETE")
            }
            // 如果加载失败了
            else if (adapterStatus == ADAPTER_STATUS_LOADING_FAIL){
                Toast.makeText(activityContext, "请检查网络", Toast.LENGTH_SHORT).show()
                notifyItemChanged(itemCount - 1)
                Log.e("dc", "statusChangingObserver >>>> ADAPTER_STATUS_LOADING_FAIL")
            }else if (preAdapterStatus == ADAPTER_STATUS_LOADING_FAIL && adapterStatus == ADAPTER_STATUS_LOADING){
                Log.e("dc", "statusChangingObserver >>>> ADAPTER_STATUS_LOADING_FAIL && ADAPTER_STATUS_LOADING")
            }
        }
    }

    /** 数据变化回调，数据从网络处获得 **/
    private var dataChangingObserver = Observer<ArrayList<PoolBean>> { netList ->
        rangeArray[0] = rangeArray[1]
        rangeArray[1] = rangeArray[0] + netList.size
        addData(netList)
    }

    init {
        if (activityContext is LifecycleOwner) {
            // 状态回调
            loadUtil.loadingStatus.observe(activityContext, statusChangingObserver)
            // 数据回调
            loadUtil.mutablelist.observe(activityContext, dataChangingObserver)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is LoadingViewHolder){
            holder.pauseLoading()
        }
    }

    override fun getItemCount(): Int {
        return newDataSource.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= newDataSource.size){
            POOL_TYPE.bottom
        }else{
            POOL_TYPE.product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(activityContext).inflate(R.layout.recycler_item_stagger_grid_2, parent, false)
        view.layoutParams?.let {
            if (it is StaggeredGridLayoutManager.LayoutParams){
                it.isFullSpan = (viewType == POOL_TYPE.bottom)
            }
        }
        return when(viewType){
            POOL_TYPE.bottom -> {
                LoadingViewHolder(view)
            }
            else -> {
                ProductViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.onBindViewHolder(newDataSource.elementAtOrNull(position), position)
            }
            is LoadingViewHolder -> {
                if (adapterStatus == ADAPTER_STATUS_LOADING) {
                    holder.onBindViewHolder(position)
                    holder.showLoading()
                    loadUtil.startLoadData()
                }else{
                    holder.pauseLoading()
                    holder.onBindViewHolder(position)
                }
            }
        }
    }

    fun addData(list : List<PoolBean>?){
        if (!list.isNullOrEmpty()){
            val startIndex = newDataSource.size
            newDataSource.addAll(list)
            val endIndex = startIndex + list.size + 1
            notifyItemChanged(startIndex)
            for (index in (startIndex + 1)..endIndex){
                notifyItemInserted(index)
            }
        }
    }

    fun clear(){
        newDataSource.clear()
    }

    fun isCanPreload() : Boolean{
        return ADAPTER_STATUS_LOADING == adapterStatus
    }

    /**
     * 当用户划过超过2分之一的商品，需要加载下一页【仅处于预备加载状态下才有效】
     */
    fun preload(firstVisiblePosition : Int) {
        if (adapterStatus != ADAPTER_STATUS_LOADING){
            return
        }
        if (rangeArray[1] <= rangeArray[0]){
            return
        }
        if (firstVisiblePosition >= rangeArray[0] && firstVisiblePosition <= rangeArray[1]){
            if (firstVisiblePosition > ((rangeArray[0] + rangeArray[1]) / 2f)){
                loadUtil.startLoadData()
            }
        }
    }

    inner class ProductViewHolder(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val progressBar : ImageView = containerView.findViewById(R.id.progress_bar)
        val text : TextView = containerView.findViewById(R.id.loading_text)
        fun onBindViewHolder(item : PoolBean?, position: Int) {
            progressBar.visibility = View.GONE
            text.text = item?.text
            containerView.setBackgroundResource(android.R.color.holo_red_dark)
            when(position % 6){
                0 -> {
                    setHeight(600)
                }
                1 -> {
                    setHeight(200)
                }
                2 -> {
                    setHeight(400)
                }
                3 -> {
                    setHeight(300)
                }
                4 -> {
                    setHeight(100)
                }
                5 -> {
                    setHeight(700)
                }
            }
        }

        private fun setHeight(heightPx : Int){
            val params = text.layoutParams as ConstraintLayout.LayoutParams
            params.height = heightPx
            text.layoutParams = params
        }
    }

    inner class LoadingViewHolder(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        val progressBar : ImageView = containerView.findViewById(R.id.progress_bar)
        val text : TextView = containerView.findViewById(R.id.loading_text)

        fun onBindViewHolder(position: Int) {

            if (position == 0){
                containerView.visibility = View.GONE
            }else{
                containerView.visibility = View.VISIBLE
            }

            if (adapterStatus == ADAPTER_STATUS_LOADING) {
                progressBar.visibility = View.VISIBLE
                text.text = "数据加载中"

                Logger.e("onBindViewHolder[${hashCode()}] 》》》 loading ................")
            }else{
                progressBar.visibility = View.GONE
                text.text = "已经加载完成了"

                Logger.e("onBindViewHolder[${hashCode()}] 》》》 complete ................")
            }

            containerView.setBackgroundResource(android.R.color.transparent)
            val params = text.layoutParams as ConstraintLayout.LayoutParams
            params.height = 100
            text.layoutParams = params
        }

        fun showLoading(){
            if (adapterStatus == ADAPTER_STATUS_LOADING) {
                progressBar.startAnimation(rotateAnimation)
            }
        }

        fun pauseLoading(){
            rotateAnimation.cancel()
            progressBar.clearAnimation()
        }
    }

    companion object{
        const val ADAPTER_STATUS_LOADING = 1
        const val ADAPTER_STATUS_NORMAL = 2
        const val ADAPTER_STATUS_LOADING_COMPLETE = 3
        const val ADAPTER_STATUS_LOADING_FAIL= 4

        /** 旋转动画 **/
        val rotateAnimation : Animation = AnimationUtils.loadAnimation(SHApplication.instance(), R.anim.anim_bottom_progress_bar_loading)
    }
}