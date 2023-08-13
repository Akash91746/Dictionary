package com.example.dictionary.feature_search.ui

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.databinding.BannerCardBinding
import com.example.dictionary.databinding.FragmentSearchBinding
import com.example.dictionary.feature_favorite.adapters.FavoriteListAdapter
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import com.example.dictionary.feature_searchDetail.ui.SearchDetailFragment
import com.example.dictionary.feature_searchHistory.adapters.SearchDataListAdapter
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), View.OnClickListener, TextView.OnEditorActionListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())

        binding.searchLayout.setOnClickListener(this)
        binding.searchInputField.setOnEditorActionListener(this)

        val favoriteAdapter = FavoriteListAdapter(object : FavoriteListAdapter.OnItemClick {
            override fun onClick(item: FavoriteWord) {
                SearchDetailFragment.navigate(
                    navController, R.id.action_nav_search_to_searchDetailFragment,
                    item.formattedWord()
                )
            }
        })

        val searchAdapter =
            SearchDataListAdapter(object : SearchDataListAdapter.OnClickItemListener {
                override fun onClick(item: SearchData) {
                    SearchDetailFragment.navigate(
                        navController,
                        R.id.action_nav_search_to_searchDetailFragment,
                        item.search
                    )
                }
            })

        setUpAdapter(favoriteAdapter, searchAdapter)


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                toggleView(binding.favoriteCard,it.favoriteWords.isEmpty())
                toggleView(binding.searchHistoryCard,it.recentSearchList.isEmpty())

                favoriteAdapter.submitList(it.favoriteWords)
                searchAdapter.submitList(it.recentSearchList)
            }
        }
    }

    private fun toggleView(binding: BannerCardBinding, isEmpty: Boolean) {
        binding.emptyAnim.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun setUpAdapter(
        favoriteAdapter: FavoriteListAdapter,
        searchAdapter: SearchDataListAdapter,
    ) {
        binding.favoriteCard.recyclerView.adapter = favoriteAdapter
        binding.favoriteCard.headerTitle.text = getString(R.string.favorites_header_title)
        binding.favoriteCard.headerActionButton.setOnClickListener {
            removeFocusAndKeyboard()
            navController.navigate(R.id.action_nav_search_to_nav_favorites)

        }

        binding.searchHistoryCard.recyclerView.adapter = searchAdapter
        binding.searchHistoryCard.headerTitle.text = getString(R.string.recent_searches_card_header)
        binding.searchHistoryCard.headerActionButton.setOnClickListener {
            removeFocusAndKeyboard()
            navController.navigate(R.id.action_nav_search_to_nav_recent)
        }
    }

    private fun removeFocusAndKeyboard() {
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

    override fun onEditorAction(view: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
        if (view?.id == binding.searchInputField.id && actionId == EditorInfo.IME_ACTION_SEARCH) {
            removeFocusAndKeyboard()

            val text = view.text.toString()
            val navigation = Navigation.findNavController(requireView())

            binding.searchInputField.text?.clear()
            SearchDetailFragment.navigate(
                navigation,
                R.id.action_nav_search_to_searchDetailFragment,
                text
            )
            return true
        }

        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}