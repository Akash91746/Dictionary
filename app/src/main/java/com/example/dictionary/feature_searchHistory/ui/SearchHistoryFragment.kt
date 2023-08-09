package com.example.dictionary.feature_searchHistory.ui

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSearchHistoryBinding
import com.example.dictionary.feature_searchHistory.adapters.SearchDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchHistoryFragment : Fragment() {

    private var _binding: FragmentSearchHistoryBinding? = null

    private val binding: FragmentSearchHistoryBinding
        get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[SearchHistoryViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchDataAdapter()
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collectLatest {
                adapter.submitList(it.searchDataList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}