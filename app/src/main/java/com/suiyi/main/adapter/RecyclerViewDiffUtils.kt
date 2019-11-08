package com.suiyi.main.adapter

import android.text.TextUtils
import androidx.recyclerview.widget.DiffUtil
import com.example.base.bean.RecyclerBean

public class RecyclerViewDiffUtils(var oldList : List<RecyclerBean>?, var newList : List<RecyclerBean>?) : DiffUtil.Callback(){

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList?.get(oldPosition)?.name == newList?.get(newPosition)?.name
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return TextUtils.equals(oldList?.get(oldPosition)?.name, newList?.get(newPosition)?.name)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return TextUtils.equals(oldList?.get(oldItemPosition)?.name, newList?.get(newItemPosition)?.name)
    }
}