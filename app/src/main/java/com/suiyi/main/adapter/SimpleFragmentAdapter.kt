package com.suiyi.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.suiyi.main.activity.fragment.SimpleRecyclerViewFragment

class SimpleFragmentAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm){

    override fun getItem(p0: Int): Fragment {
        return SimpleRecyclerViewFragment()
    }

    override fun getCount(): Int {
        return 5
    }


}