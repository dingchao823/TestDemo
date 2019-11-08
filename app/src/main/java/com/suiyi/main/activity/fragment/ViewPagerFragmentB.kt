package com.suiyi.main.activity.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.suiyi.main.R
import com.suiyi.main.activity.NestedViewPagerActivity

/**
 * 用于演示的fragment
 *
 */
class ViewPagerFragmentB : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_view_pager_2, null)
        view.findViewById<Button>(R.id.button).setOnClickListener{
            activity?.let {
                if (it is NestedViewPagerActivity){
                    it.change(1)
                }
            }
        }

        Log.e("dc", "【ViewPagerFragmentB】 create view")

        return view
    }

}