package com.example.dictionary.feature_searchDetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_WORD = "word-arg"

@AndroidEntryPoint
class SearchDetailFragment : Fragment() {

    private var wordParam: String? = null

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[SearchDetailViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           wordParam =  it.getString(ARG_WORD)
        }

        wordParam?.let {
            viewModel.init(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(word: String) = SearchDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_WORD,word)
            }
        }
    }
}