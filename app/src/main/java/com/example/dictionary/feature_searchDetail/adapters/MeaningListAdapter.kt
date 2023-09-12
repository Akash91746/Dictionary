package com.example.dictionary.feature_searchDetail.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
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
            val dataLayout = binding.definitionLayout

            setTextAndVisibility(dataLayout.definition,data.definition, R.string.word_definition)
            setTextAndVisibility(dataLayout.example,data.example,R.string.word_example)

            setTextAndVisibility(dataLayout.antonyms,convertListToString(data.antonyms),R.string.word_antonyms)
            setTextAndVisibility(dataLayout.synonyms,convertListToString(data.synonyms),R.string.word_synonyms)
        }

        private fun convertListToString(list: List<String>): String? {
            if (list.isEmpty()) return null

            return list.joinToString(separator = ", ")
        }

        private fun setTextAndVisibility(view: TextView,value: String?,@StringRes resId: Int? = null) {
            if(value != null) {
                if(resId != null) {
                    view.text = binding.root.context.getString(resId,value)
                }else {
                    view.text = value
                }
            }

            setVisibility(view,value != null)
        }
        private fun setVisibility(view: View,visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.GONE
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