package com.suiyi.main.base

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.suiyi.main.SHApplication
import kotlinx.android.extensions.LayoutContainer

/**
 * 首页基础适配器
 *
 * @author 0004640
 */
abstract class BaseDelegateAdapter<ViewHolder: RecyclerView.ViewHolder, DataType>  : DelegateAdapter.Adapter<ViewHolder>(){

    object ViewType{
        const val HOME_VIEW_TYPE_BANNER = 1
        const val HOME_VIEW_TYPE_SELL_POINT = 2
        const val HOME_VIEW_TYPE_DIVIDER = 3
        const val HOME_VIEW_TYPE_SCROLL_2_ROW = 4
        const val HOME_VIEW_TYPE_SCROLL_2_ROW_INNER = 41
        const val HOME_VIEW_TYPE_1ROW_3COL = 5
        const val HOME_VIEW_TYPE_1_IMAGE = 6
        const val HOME_VIEW_TYPE_FLASH_SALE = 7
        const val HOME_VIEW_TYPE_FLASH_SALE_GOODS = 8
        const val HOME_VIEW_TYPE_NOTIFY = 9
        const val HOME_VIEW_TYPE_CHARACTERISTIC_AREA = 10
        const val HOME_VIEW_TYPE_VIEW_PAGER = 11
        const val HOME_VIEW_TYPE_VIEW_PAGER_PRODUCT = 12
        const val HOME_VIEW_TYPE_VIEW_PAGER_BOTTOM_SLOGAN = 13
        const val SIMPLE_TEXT_WITH_DIFF = 14
    }

    /** 使能 DiffUtils **/
    var enableDiffUtils = false

    /** 这是 Application 的 context，如果出现 xml error inflater <unknown> 错误，传入 activity context **/
    var context : Context? = SHApplication.instance()

    /** 当前绑定到的 RecyclerView **/
    var fatherRecyclerView : RecyclerView? = null

    /** 数据源 **/
    protected var newDataSource : ArrayList<DataType> = ArrayList()

    /** 老数据源 **/
    protected var oldDataSource : ArrayList<DataType> = ArrayList()

    /** DiffUtil 回调 **/
    private var diffUtilCallBack : DiffUtil.Callback = object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return this@BaseDelegateAdapter.areItemsTheSame(oldItemPosition, newItemPosition)
        }

        override fun getOldListSize(): Int {
            return this@BaseDelegateAdapter.getOldListSize()
        }

        override fun getNewListSize(): Int {
            return this@BaseDelegateAdapter.getNewListSize()
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return this@BaseDelegateAdapter.areContentsTheSame(oldItemPosition, newItemPosition)
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return this@BaseDelegateAdapter.getChangePayload(oldItemPosition, newItemPosition)
        }
    }

    /**
     * 获取 viewType
     */
    abstract fun getViewType(position: Int) : Int

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    override fun getItemCount(): Int {
        return newDataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        payloads.let {
            if (payloads.isEmpty()){
                onBindViewHolder(holder, position)
                return
            }
            payloads[0].let {
                if (it is Bundle){
                    onBindViewHolderInner(it, holder, position)
                }else{
                    onBindViewHolder(holder, position)
                }
            }
        }
    }

    /**
     * 单独到 View 的刷新
     */
    open fun onBindViewHolderInner(bundle: Bundle, holder: ViewHolder?, position: Int){

    }

    /**
     * 单个数据类型是否相同
     */
    open fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (!enableDiffUtils){
            return false
        }
        return true
    }

    /**
     * 老数据数量
     */
    fun getOldListSize(): Int {
        return oldDataSource.size
    }

    /**
     * 新数据数量
     */
    fun getNewListSize(): Int{
        return newDataSource.size
    }

    /**
     * 单个数据的内容是否相同，实现对应 bean.equals(Any?) 方法
     */
    open fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldDataSource.elementAtOrNull(oldItemPosition)
        val newItem = newDataSource.elementAtOrNull(newItemPosition)
        return oldItem?.equals(newItem) ?: false
    }

    /**
     * 满足如下内容，部分刷新，精确到 view
     * 1. areItemsTheSame = true
     * 2. areContentsTheSame = false
     */
    open fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any?{
        return null
    }

    /**
     * 设置数据源，每个 adapter 有自己独立的数据源
     */
    fun setDataSource(dataList : ArrayList<DataType>?){
        dataList.let {
            if (it == null || it.isEmpty()){
                return
            }
            clearOldAndCopy()
            newDataSource.clear()
            newDataSource.addAll(it)
            notifyAdapter()
        }
    }

    /**
     * 添加单个数据
     */
    fun addData(data: DataType?) {
        data.let {
            if (it != null) {
                clearOldAndCopy()
                newDataSource.add(it)
                notifyAdapter()
            }
        }
    }

    /**
     * 添加数据列表
     */
    fun addDataList(dataList : ArrayList<DataType>?){
        dataList.let {
            if (it == null || it.isEmpty()){
                return
            }
            clearOldAndCopy()
            newDataSource.addAll(it)
            notifyAdapter()
        }
    }

    /**
     * 先清除数据再添加新数据
     */
    fun addDataListWithClear(dataList : ArrayList<DataType>?){
        clearData()
        addDataList(dataList)
    }

    /**
     * 移除数据
     */
    fun removeData(data: DataType){
        data.let {
            if (data == null){
                return
            }
            clearOldAndCopy()
            newDataSource.remove(data)
        }
    }

    /**
     * 清除数据
     */
    fun clearData(){
        newDataSource.clear()
        oldDataSource.clear()
    }

    /**
     * 清楚旧数据并 copy 新数据
     */
    private fun clearOldAndCopy(){
        oldDataSource.clear()
        oldDataSource.addAll(newDataSource)
    }

    /**
     * 刷新适配器
     */
    private fun notifyAdapter(){
        if (enableDiffUtils) {
            notifyDiffUpdate()
        }else{
            notifyDataSetChanged()
        }
    }

    /**
     * 进行一次差异分析
     */
    private fun notifyDiffUpdate(){
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        diffResult.let {
            if (it != null){
                diffResult.dispatchUpdatesTo(this)
            }
        }
    }

    fun getRootView(parent: ViewGroup?, layoutId : Int) : View?{
        return LayoutInflater.from(context).inflate(layoutId, parent, false)
    }

    /**
     * 用于应对 Application Context 传入造成的问题
     */
    fun getRootView(activityContext : Context, parent: ViewGroup?, layoutId : Int) : View?{
        return LayoutInflater.from(activityContext).inflate(layoutId, parent, false)
    }

    abstract class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        abstract fun onBindViewHolder(position : Int)

    }
}