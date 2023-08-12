package com.example.dictionary.feature_searchHistory.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.common.adapters.BaseListAdapter
import com.example.dictionary.databinding.SearchDataItemBinding
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import java.time.format.DateTimeFormatter

class SearchDataListAdapter(
    private val clickListener : OnClickItemListener? = null
): BaseListAdapter<SearchData,SearchDataListAdapter.ViewHolder>() {

    private  val timeStampFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent,clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data,timeStampFormatter)
    }

    class ViewHolder(
        private val binding: SearchDataItemBinding,
        private val clickListener: OnClickItemListener? = null
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(data:SearchData,formatter: DateTimeFormatter){
            binding.searchTitle.text = data.search
            binding.timeStamp.text = data.createdAt.format(formatter)

            clickListener?.let {
                binding.root.setOnClickListener { clickListener.onClick(data) }
            }
        }

        companion object {
            fun from(parent: ViewGroup,clickListener: OnClickItemListener?): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = SearchDataItemBinding.inflate(inflater,parent,false)
                return ViewHolder(binding,clickListener)
            }
        }
    }

    interface OnClickItemListener {
        fun onClick(item: SearchData)
    }
}