package com.example.dictionary.feature_search.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSearchBinding
import com.example.dictionary.feature_searchDetail.ui.SearchDetailFragment

class SearchFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchLayout.setOnClickListener(this)

        binding.searchInputField.setOnEditorActionListener { textView, actionId, _ ->

            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                removeFocusAndKeyboard()

                val text = textView.text.toString()
                val navigation = Navigation.findNavController(requireView())


                val bundle = Bundle().apply {
                    putString(SearchDetailFragment.ARG_WORD,text)
                }

                binding.searchInputField.text?.clear()
                navigation.navigate(R.id.action_nav_search_to_searchDetailFragment,bundle)
                return@setOnEditorActionListener true
            }

            false
        }
    }

    private fun removeFocusAndKeyboard(){
        if (binding.searchInputField.hasFocus()) {
            binding.searchInputField.clearFocus()
        }

        val inm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onClick(v: View) {
        if (binding.searchLayout.id == v.id) {
            removeFocusAndKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}