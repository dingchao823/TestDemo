package com.suiyi.main.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.bean.PagingBean
import com.suiyi.main.R
import com.suiyi.main.adapter.PagingAdapter
import com.example.base.constants.Path
import com.suiyi.main.mvvm.models.JetPackViewModel
import kotlinx.android.synthetic.main.activity_jetpack.*

/**
 * 使用 JetPack 组件实现一些东西
 *
 * @author dingchao823
 */
@Route(path = Path.JET_PACK_ACTIVITY)
class PagingActivity : AppCompatActivity(), View.OnClickListener {

    var adapter : PagingAdapter? = null

    lateinit var mModel : JetPackViewModel

    private val listObservable = Observer<PagedList<PagingBean>?> {
        adapter?.submitList(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)

        mModel = ViewModelProviders.of(this).get(JetPackViewModel::class.java)

        adapter = PagingAdapter(this, this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        initRecyclerView()

        function.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        initRecyclerView()

    }

    private fun initRecyclerView() {
        mModel.list?.removeObserver(listObservable)
        mModel.initList()
        mModel.list?.observe(this, listObservable)
    }
}