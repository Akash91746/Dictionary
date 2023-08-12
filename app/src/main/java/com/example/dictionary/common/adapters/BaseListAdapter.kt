package com.example.dictionary.common.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.common.models.BaseModel

abstract class BaseListAdapter<T : BaseModel, VH : RecyclerView.ViewHolder> :
    ListAdapter<T, VH>(BaseDiffUtil<T>()) {
    class BaseDiffUtil<U : BaseModel> : DiffUtil.ItemCallback<U>() {
        override fun areItemsTheSame(oldItem: U, newItem: U): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: U, newItem: U): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}