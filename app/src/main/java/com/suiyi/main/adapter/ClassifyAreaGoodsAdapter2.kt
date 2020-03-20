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
import com.suiyi.main.R
import com.suiyi.main.SHApplication
import com.suiyi.main.bean.PoolBean
import com.suiyi.main.constants.POOL_TYPE
import com.suiyi.main.utils.GoodsLoadUtil
import kotlinx.android.extensions.LayoutContainer
import kotlin.collections.ArrayList

/**
 * 瀑布类适配器
 */
class ClassifyAreaGoodsAdapter2(val activityContext: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /** 当前适配器状态 **/
    var adapterStatus = ADAPTER_STATUS_LOADING
    var preAdapterStatus = adapterStatus
    /** 数据源 **/
    var newDataSource : ArrayList<PoolBean> = ArrayList()
    /** 老数据源 **/
    var oldDataSource : ArrayList<PoolBean> = ArrayList()
    /** 数据差分器 **/
    private var differ = MyItemCallBack()
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
        if(!newDataSource.isNullOrEmpty()){
            // 如果加载的是Loading数据，移除
            val lastItem = newDataSource.elementAtOrNull(newDataSource.size - 1)
            if(lastItem?.type == POOL_TYPE.loading || lastItem?.type == POOL_TYPE.slogan){
                newDataSource.removeAt(newDataSource.size - 1)
                notifyItemRemoved(newDataSource.size)
            }
        }
        if (adapterStatus == ADAPTER_STATUS_LOADING){
            netList.add(PoolBean(POOL_TYPE.loading))
        }
        else if (adapterStatus == ADAPTER_STATUS_LOADING_COMPLETE) {
            netList.add(PoolBean(POOL_TYPE.slogan))
        }
        addData(netList)
    }

    init {
        if (activityContext is LifecycleOwner) {
            // 状态回调
            loadUtil.loadingStatus.observe(activityContext, statusChangingObserver)
            // 数据回调
            loadUtil.mutablelist.observe(activityContext, dataChangingObserver)
        }

        // 添加一个用于加载 bean
        val list = ArrayList<PoolBean>()
        list.add(PoolBean(POOL_TYPE.loading))
        addData(list)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is LoadingViewHolder){
            holder.showLoading()
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is LoadingViewHolder){
            holder.pauseLoading()
        }
    }

    override fun getItemCount(): Int {
        return newDataSource.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = newDataSource.elementAtOrNull(position)
        return item?.type ?: POOL_TYPE.product
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(activityContext).inflate(R.layout.recycler_item_stagger_grid_2, parent, false)
        view.layoutParams?.let {
            if (it is StaggeredGridLayoutManager.LayoutParams){
                it.isFullSpan = (viewType == POOL_TYPE.slogan || viewType == POOL_TYPE.loading)
            }
        }
        when(viewType){
            POOL_TYPE.slogan -> {
                return CompleteViewHolder(view)
            }
            POOL_TYPE.loading -> {
                return LoadingViewHolder(view)
            }
            else -> {
                return ProductViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
//                Log.e("dc", "onBindViewHolder >>>> ProductViewHolder")
                holder.onBindViewHolder(newDataSource.elementAtOrNull(position), position)
            }
            is LoadingViewHolder -> {
//                Log.e("dc", "onBindViewHolder >>>> LoadingViewHolder")
                holder.onBindViewHolder()
                holder.showLoading()
                // 当前有数据才会触发上拉加载更多
                if (itemCount >= 1) {
                    loadUtil.startLoadData()
                }
            }
            is CompleteViewHolder -> {
//                Log.e("dc", "onBindViewHolder >>>> CompleteViewHolder")
                holder.onBindViewHolder()
            }
        }
    }

    fun addData(list : List<PoolBean>?){
        if (!list.isNullOrEmpty()){
            oldDataSource.clear()
            oldDataSource.addAll(newDataSource)
            newDataSource.addAll(list)
            notifyDiffUpdate()
        }
    }

    fun clear(){
        newDataSource.clear()
        oldDataSource.clear()
    }

    fun isCanPreload() : Boolean{
        return ADAPTER_STATUS_LOADING == adapterStatus
    }

    /**
     * 当用户划过超过2分之一的商品，需要加载下一页【仅处于预备加载状态下才有效】
     */
    fun preload() {
        if (adapterStatus == ADAPTER_STATUS_LOADING){
            loadUtil.startLoadData()
        }
    }

    /**
     * 进行一次差异分析
     */
    private fun notifyDiffUpdate(){
        DiffUtil.calculateDiff(differ).dispatchUpdatesTo(this)
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

    class LoadingViewHolder(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        val progressBar : ImageView = containerView.findViewById(R.id.progress_bar)
        val text : TextView = containerView.findViewById(R.id.loading_text)
        fun onBindViewHolder() {
            progressBar.visibility = View.VISIBLE
            text.text = "数据加载中"

            containerView.setBackgroundResource(android.R.color.transparent)
            val params = text.layoutParams as ConstraintLayout.LayoutParams
            params.height = 100
            text.layoutParams = params
        }

        fun showLoading(){
            progressBar.startAnimation(rotateAnimation)
        }

        fun pauseLoading(){
            rotateAnimation.cancel()
            progressBar.clearAnimation()
        }
    }

    class CompleteViewHolder(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        val progressBar : ImageView = containerView.findViewById(R.id.progress_bar)
        val text : TextView = containerView.findViewById(R.id.loading_text)
        fun onBindViewHolder() {
            progressBar.visibility = View.GONE
            text.text = "已经到底啦"

            containerView.setBackgroundResource(android.R.color.transparent)
            val params = text.layoutParams as ConstraintLayout.LayoutParams
            params.height = 100
            text.layoutParams = params
        }
    }

    inner class MyItemCallBack : DiffUtil.Callback(){

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val isItemSame = oldDataSource.elementAtOrNull(oldItemPosition) == newDataSource.elementAtOrNull(newItemPosition)
            Log.e("dc", "DiffUtil.Callback **** old位置$oldItemPosition **** new位置$newItemPosition **** item相同？$isItemSame")
            return isItemSame
        }

        override fun getOldListSize(): Int {
            return oldDataSource.size
        }

        override fun getNewListSize(): Int {
            return newDataSource.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldDataProductId = oldDataSource.elementAtOrNull(oldItemPosition)?.text
            val newDataProductId = newDataSource.elementAtOrNull(newItemPosition)?.text
            val isContentSame = (oldDataProductId?.equals(newDataProductId) == true)
            Log.e("dc", "DiffUtil.Callback **** old位置$oldItemPosition **** new位置$newItemPosition **** content相同？$isContentSame")
            return isContentSame
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