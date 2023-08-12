package com.example.dictionary.feature_searchHistory.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSearchHistoryBinding
import com.example.dictionary.feature_searchHistory.adapters.SearchDataListAdapter
import com.example.dictionary.common.helpers.SwipeToDeleteTouchHelper
import com.example.dictionary.feature_searchDetail.ui.SearchDetailFragment
import com.example.dictionary.feature_searchHistory.domain.models.SearchData
import com.example.dictionary.feature_searchHistory.utils.SearchHistoryEvents
import com.google.android.material.snackbar.Snackbar
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
        _binding = FragmentSearchHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navigator = Navigation.findNavController(requireView())

        val adapter = SearchDataListAdapter(object : SearchDataListAdapter.OnClickItemListener {
            override fun onClick(item: SearchData) {
                SearchDetailFragment.navigate(
                    navigator,
                    R.id.action_nav_recent_to_searchDetailFragment,
                    item.search
                )
            }
        })

        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collectLatest {
                adapter.submitList(it.searchDataList)
            }
        }

        enableSwipeToDeleteAndUndo(adapter)
    }

    private fun enableSwipeToDeleteAndUndo(adapter: SearchDataListAdapter) {

        val swipeToDeleteTouchHelper = object : SwipeToDeleteTouchHelper(
            requireContext()
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.currentList[position]
                viewModel.onEvent(SearchHistoryEvents.OnDeleteItem(item))

                showUndoSnackBar()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteTouchHelper)

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun showUndoSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.undo_delete_text), Snackbar.LENGTH_LONG
        )

        snackBar.setAction(getString(R.string.undo_delete_button_text)) {
            viewModel.onEvent(SearchHistoryEvents.OnUndoDelete)
        }

        snackBar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}