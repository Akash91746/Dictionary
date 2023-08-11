package com.example.dictionary.feature_favorite.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentFavoriteBinding
import com.example.dictionary.databinding.FragmentSearchBinding
import com.example.dictionary.feature_favorite.adapters.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel by lazy { ViewModelProvider(this)[FavoriteViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteAdapter()
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collectLatest {
                adapter.submitList(it.list)
            }
        }
    }

}