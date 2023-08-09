package com.example.dictionary.feature_searchDetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSearchBinding
import com.example.dictionary.databinding.FragmentSearchDetailBinding
import com.example.dictionary.feature_searchDetail.adapters.MeaningListAdapter
import com.example.dictionary.feature_searchDetail.domain.models.WordData
import com.example.dictionary.feature_searchDetail.domain.models.WordDataItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class SearchDetailFragment : Fragment() {

    private var wordParam: String? = null

    private var _binding: FragmentSearchDetailBinding? = null
    private val binding: FragmentSearchDetailBinding
        get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[SearchDetailViewModel::class.java] }
    private lateinit var adapter: MeaningListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordParam = it.getString(ARG_WORD)
        }

        wordParam?.let {
            viewModel.init(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchDetailBinding.inflate(inflater, container, false)

        adapter = MeaningListAdapter()
        binding.dataLayout.meaningRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->

                binding.progress.visibility = if (state.loading) View.VISIBLE else View.GONE

                if (state.data != null) {
                    populateDate(state.data)
                } else if (state.error != null) {
                    showErrorSnackBar(state.error)
                }
            }
        }

        return binding.root
    }

    private fun populateDate(data: WordDataItem){
        binding.dataLayout.data = data

        adapter.submitList(data.meanings)
    }

    private fun showErrorSnackBar(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                viewModel.init(wordParam!!)
            }
            .setActionTextColor(requireContext().getColor(R.color.error))
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_WORD = "word-arg"

        @JvmStatic
        fun newInstance(word: String) = SearchDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_WORD, word)
            }
        }
    }
}