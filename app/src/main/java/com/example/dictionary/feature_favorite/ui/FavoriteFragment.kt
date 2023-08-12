package com.example.dictionary.feature_favorite.ui

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
import com.example.dictionary.common.helpers.SwipeToDeleteTouchHelper
import com.example.dictionary.databinding.FragmentFavoriteBinding
import com.example.dictionary.feature_favorite.adapters.FavoriteListAdapter
import com.example.dictionary.feature_favorite.domain.models.FavoriteWord
import com.example.dictionary.feature_favorite.utils.FavoriteScreenEvents
import com.example.dictionary.feature_searchDetail.ui.SearchDetailFragment
import com.google.android.material.snackbar.Snackbar
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

        val navController = Navigation.findNavController(requireView())

        val adapter = FavoriteListAdapter(object : FavoriteListAdapter.OnItemClick {
            override fun onClick(item: FavoriteWord) {
                SearchDetailFragment.navigate(
                    navController,
                    R.id.action_nav_favorites_to_searchDetailFragment,
                    item.word.lowercase()
                )
            }
        })

        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collectLatest {
                adapter.submitList(it.list)
            }
        }

        enableSwipeToDeleteAndUndo(adapter)
    }

    private fun enableSwipeToDeleteAndUndo(adapter: FavoriteListAdapter) {

        val swipeToDeleteTouchHelper = object : SwipeToDeleteTouchHelper(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.currentList[position]
                viewModel.onEvent(FavoriteScreenEvents.OnDelete(item))

                showUndoSnackBar()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteTouchHelper)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun showUndoSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.undo_delete_text),
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(getString(R.string.undo_delete_button_text)) {
            viewModel.onEvent(FavoriteScreenEvents.OnUndoDelete)
        }

        snackBar.show()
    }

}