package com.example.dictionary.feature_favorite.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.common.adapters.BaseListAdapter
import com.example.dictionary.databinding.FavoriteListItemBinding
import com.example.dictionary.databinding.SearchDataItemBinding
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord

class FavoriteListAdapter(
    private val clickListener: OnItemClick? = null
) : BaseListAdapter<FavoriteWord, FavoriteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item,clickListener)
    }

    class ViewHolder(
        private val binding: FavoriteListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(word: FavoriteWord,clickListener: OnItemClick?) {
            val text = word.word.substring(0,1) + word.word.substring(1).lowercase()
            binding.favoriteTitle.text = text

            clickListener?.let {
                binding.root.setOnClickListener {
                    clickListener.onClick(word)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = FavoriteListItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    interface OnItemClick {
        fun onClick(item: FavoriteWord)
    }
}