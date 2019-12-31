package com.suiyi.main.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suiyi.main.R
import com.example.base.bean.PagingBean
import com.suiyi.main.base.BaseDelegateAdapter
import com.suiyi.main.mvvm.source.PageDataSource
import com.suiyi.main.utils.DimenUtils
import kotlinx.android.extensions.LayoutContainer

class PagingAdapter(val context: Context, val owner : LifecycleOwner): PagedListAdapter<PagingBean, RecyclerView.ViewHolder>(MyItemCallBack()) {

    val TYPE_LOAD_LOADING = 1002
    val TYPE_LOAD_ALL_COMPLETE = 1003
    val TYPE_LOAD_FAIL = 1004
    val TYPE_NORMAL = 1005
    var state : Int = TYPE_LOAD_LOADING
    var rotateAnimation: Animation? = AnimationUtils.loadAnimation(context, R.anim.anim_bottom_loading)

    override fun getItemCount(): Int {
        if (state == TYPE_LOAD_LOADING || state == TYPE_LOAD_ALL_COMPLETE || state == TYPE_LOAD_FAIL){
            return super.getItemCount() + 1
        }
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1 && state == TYPE_LOAD_LOADING){
            return TYPE_LOAD_LOADING
        }
        if (position == itemCount - 1 && state == TYPE_LOAD_ALL_COMPLETE){
            return TYPE_LOAD_ALL_COMPLETE
        }
        if (position == itemCount - 1 && state == TYPE_LOAD_FAIL){
            return TYPE_LOAD_FAIL
        }
        return super.getItemViewType(position)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_LOAD_LOADING || viewType == TYPE_LOAD_ALL_COMPLETE || viewType == TYPE_LOAD_FAIL){
            val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_bottom_loading, parent, false)
            return LoadingViewHolder(view)
        }else {
            val textView = TextView(context)
            textView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtils.dipTopx(context, 100f))
            textView.setTextColor(context.resources.getColor(R.color.white))
            textView.gravity = Gravity.CENTER
            textView.setBackgroundColor(Color.parseColor("#000000"))
            return NormalViewHolder(textView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when{
            holder is NormalViewHolder -> {
                holder.onBindViewHolder(position)
            }
            holder is LoadingViewHolder -> {
                holder.onBindViewHolder(position)
            }
        }

    }

    override fun onCurrentListChanged(previousList: PagedList<PagingBean>?, currentList: PagedList<PagingBean>?) {
        super.onCurrentListChanged(previousList, currentList)

        val pageList = getCurrentList()
        val dataSource = pageList?.dataSource
        if (dataSource is PageDataSource){
            dataSource.loadingState.observe(owner, listObservable)
        }
    }

    private val listObservable = Observer<Int> {
        state = it
        notifyItemChanged(itemCount)
    }

    inner class NormalViewHolder(itemView: View) : BaseDelegateAdapter.ViewHolder(itemView){

        override fun onBindViewHolder(position : Int) {
            if (itemView is TextView){
                itemView.text = getItem(position)?.name
            }
        }

    }

    inner class LoadingViewHolder(override val containerView: View) : BaseDelegateAdapter.ViewHolder(containerView), LayoutContainer {

        private val loadingText : TextView = containerView.findViewById(R.id.loading_text)
        private val loadingImage : ImageView = containerView.findViewById(R.id.progress_bar)

        override fun onBindViewHolder(position : Int) {

            when(getItemViewType(position)){
                TYPE_LOAD_LOADING -> {
                    loadingText.text = "努力加载中"
                }
                TYPE_LOAD_ALL_COMPLETE -> {
                    loadingText.text = "加载完成"
                }
                TYPE_LOAD_FAIL -> {
                    loadingText.text = "加载失败"
                }
            }

        }

        fun showLoading(){
            loadingImage.startAnimation(rotateAnimation)
        }

        fun pauseLoading(){
            loadingImage.clearAnimation()
        }

    }

    class MyItemCallBack : DiffUtil.ItemCallback<PagingBean>(){

        override fun areItemsTheSame(oldItem: PagingBean, newItem: PagingBean): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PagingBean, newItem: PagingBean): Boolean {
            return oldItem == newItem
        }

    }

}