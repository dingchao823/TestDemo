package com.suiyi.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.constants.Path
import com.suiyi.main.R
import com.suiyi.main.adapter.ClassifyAreaGoodsAdapter2
import com.suiyi.main.adapter.ClassifyAreaGoodsAdapter3
import com.suiyi.main.mvvm.models.RvLoadMoreViewModel
import com.suiyi.main.widget.StaggeredDividerItemDecoration
import kotlinx.android.synthetic.main.activity_recycler_load_more.*

/**
 * 实现上拉加载更多
 *
 * @see RvLoadMoreViewModel
 * @see ClassifyAreaGoodsAdapter2
 */
@Route(path = Path.RECYLER_VIEW_LOAD_MORE_ACTIVITY)
class RecyclerLoadMoreActivity : AppCompatActivity() {

    private var model : RvLoadMoreViewModel? = null
    private lateinit var layoutManager : StaggeredGridLayoutManager
    private val mGoodsAdapter = ClassifyAreaGoodsAdapter3(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_load_more)

        model = ViewModelProviders.of(this).get(RvLoadMoreViewModel::class.java)

        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = mGoodsAdapter
        recycler_view.addItemDecoration(StaggeredDividerItemDecoration(this, 5))
        recycler_view.addOnScrollListener(ScrollListener())
        closeDefaultAnimator(recycler_view)
    }

    private fun closeDefaultAnimator(recyclerView: RecyclerView?) {
        recyclerView?.let {
            it.itemAnimator?.addDuration = 0
            it.itemAnimator?.changeDuration = 0
            it.itemAnimator?.moveDuration = 0
            it.itemAnimator?.removeDuration = 0
            (it.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    /**
     * 实现当商品滑动超过一半时直接加载，不再等到底部再加载
     */
    private inner class ScrollListener : RecyclerView.OnScrollListener() {

        private val positions = IntArray(2)

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (mGoodsAdapter.isCanPreload()) {
                layoutManager.findFirstVisibleItemPositions(positions)
                mGoodsAdapter.itemCount.let { itemCount ->
                    positions.elementAtOrNull(0)?.let { firstItemPosition ->
                        mGoodsAdapter.preload(firstItemPosition)
                    }
                }
            }
        }

    }
}
