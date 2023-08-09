package com.example.dictionary.feature_searchHistory.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.feature_searchHistory.domain.models.SearchData

class SearchDataAdapter: ListAdapter<SearchData,SearchDataAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data)
    }

    class ViewHolder(
        private val item: View
    ): RecyclerView.ViewHolder(item) {

        fun onBind(data:SearchData){
            val title = item.findViewById<TextView>(R.id.search_title)
            title.text = data.search
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                return ViewHolder(inflater.inflate(R.layout.search_data_item,parent,false))
            }
        }

    }

    object DiffUtilCallback: DiffUtil.ItemCallback<SearchData>() {
        override fun areItemsTheSame(oldItem: SearchData, newItem: SearchData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchData, newItem: SearchData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
}