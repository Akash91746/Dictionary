package com.example.dictionary.feature_search.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var dataBinding: FragmentSearchBinding
    private val viewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java]}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dataBinding = FragmentSearchBinding.inflate(inflater,container,false)

        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.searchInputField.addTextChangedListener {

        }
    }

}