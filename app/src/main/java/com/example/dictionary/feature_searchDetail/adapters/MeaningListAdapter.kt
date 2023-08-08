package com.example.dictionary.feature_searchDetail.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.MeaningListItemBinding
import com.example.dictionary.feature_searchDetail.domain.models.Meaning

class MeaningListAdapter: ListAdapter<Meaning, MeaningListAdapter.ViewHolder>(MeaningDiffCallback) {

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

            if(data.definitions.isNotEmpty()){
                val definition = data.definitions.first()
                binding.definitionLayout.data = definition
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = MeaningListItemBinding.inflate(inflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    object MeaningDiffCallback : DiffUtil.ItemCallback<Meaning> (){
        override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem.partOfSpeech == newItem.partOfSpeech
        }

        override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


}