package com.example.dictionary.feature_searchDetail.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.common.adapters.BaseListAdapter
import com.example.dictionary.databinding.MeaningListItemBinding
import com.example.dictionary.feature_searchDetail.data.dto.MeaningDto
import com.example.dictionary.feature_searchDetail.domain.models.Meaning

class MeaningListAdapter: BaseListAdapter<Meaning, MeaningListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data)
    }

    class ViewHolder(
        private val binding: MeaningListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Meaning){
            binding.partOfSpeech.text = data.partOfSpeech
            binding.definitionLayout.data = data
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = MeaningListItemBinding.inflate(inflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
}