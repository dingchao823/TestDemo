package com.suiyi.main.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.suiyi.main.activity.fragment.SimpleRecyclerViewFragment

class SimpleFragmentAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm){

    override fun getItem(p0: Int): Fragment {
        val fragment = SimpleRecyclerViewFragment()
        val bundle = Bundle()
        bundle.putInt("index", p0)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return 5
    }


}