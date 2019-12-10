package com.suiyi.main.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.bean.PagingBean
import com.suiyi.main.R
import com.suiyi.main.adapter.PagingAdapter
import com.suiyi.main.constants.Path
import com.suiyi.main.model.JetPackViewModel
import com.suiyi.main.model.source.PageDataSourceFactory
import kotlinx.android.synthetic.main.activity_jetpack.*

/**
 * 使用 JetPack 组件实现一些东西
 *
 * @author dingchao823
 */
@Route(path = Path.JET_PACK_ACTIVITY)
class JetPackActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var adapter : PagingAdapter

    lateinit var mModel : JetPackViewModel

    private val listObservable = Observer<PagedList<PagingBean>?> {
        adapter.submitList(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)

        mModel = ViewModelProviders.of(this).get(JetPackViewModel::class.java)


        val layoutManager = LinearLayoutManager(this)
        adapter = PagingAdapter(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter

        function.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        mModel.list?.observe(this, listObservable)
        mModel.sourceFactory.source.isEnableLoading = true


    }
}