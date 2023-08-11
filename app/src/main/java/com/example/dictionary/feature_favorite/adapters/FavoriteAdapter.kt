package com.example.dictionary.feature_favorite.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.SearchDataItemBinding
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord

class FavoriteAdapter :
    ListAdapter<FavoriteWord, FavoriteAdapter.ViewHolder>(FavoriteWordDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    class ViewHolder(
        private val binding: SearchDataItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: FavoriteWord) {
            val text = word.word.substring(0,1) + word.word.substring(1).lowercase()
            binding.searchTitle.text = text
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SearchDataItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    object FavoriteWordDiffUtil : DiffUtil.ItemCallback<FavoriteWord>() {
        override fun areItemsTheSame(oldItem: FavoriteWord, newItem: FavoriteWord): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteWord, newItem: FavoriteWord): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
}