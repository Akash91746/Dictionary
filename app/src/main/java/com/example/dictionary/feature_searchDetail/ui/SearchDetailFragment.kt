package com.example.dictionary.feature_searchDetail.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSearchDetailBinding
import com.example.dictionary.feature_searchDetail.adapters.MeaningListAdapter
import com.example.dictionary.feature_searchDetail.data.dto.WordDataItemDto
import com.example.dictionary.feature_searchDetail.domain.models.WordData
import com.example.dictionary.feature_searchDetail.utils.SearchDetailScreenEvent
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

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordParam = it.getString(ARG_WORD)
        }

        wordParam?.let {
            viewModel.onEvent(SearchDetailScreenEvent.InitData(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchDetailBinding.inflate(inflater, container, false)

        adapter = MeaningListAdapter()
        binding.dataLayout.meaningRecyclerView.adapter = adapter

        val favoriteButton = binding.dataLayout.favoriteButton

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->

                binding.progress.visibility = if (state.loading) View.VISIBLE else View.GONE

                if (state.data != null) {
                    populateDate(state.data)
                } else if (state.error != null) {
                    showErrorSnackBar(state.error)
                }

                if (state.favorite) {
                    favoriteButton.setBackgroundResource(R.drawable.baseline_star_24)
                } else {
                    favoriteButton.setBackgroundResource(R.drawable.baseline_star_outline_24)
                }

                favoriteButton.isChecked = state.favorite
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dataLayout.favoriteButton.setOnClickListener {
            viewModel.onEvent(SearchDetailScreenEvent.OnClickFavorite)
        }

    }

    private fun populateDate(data: WordData) {
        binding.dataLayout.data = data

        adapter.submitList(data.meanings)

        if (data.phonetic.audio == null) return

        mediaPlayer = MediaPlayer().apply {
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
            setDataSource(data.phonetic.audio)
            prepareAsync()

            val mediaPlayButton = binding.dataLayout.mediaPlayButton

            setOnPreparedListener {
                mediaPlayButton.visibility = View.VISIBLE
                mediaPlayButton.setOnClickListener {
                    start()
                    mediaPlayButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.baseline_pause_24
                        )
                    )
                }
            }

            setOnCompletionListener {
                mediaPlayButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.baseline_play_arrow_24
                    )
                )
            }
        }
    }

    private fun showErrorSnackBar(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                if (wordParam != null)
                    viewModel.onEvent(SearchDetailScreenEvent.InitData(wordParam!!))
            }
            .setActionTextColor(requireContext().getColor(R.color.error))
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaPlayer?.release()
        mediaPlayer = null
    }

    companion object {
        const val ARG_WORD = "word-arg"

        fun navigate(
            navController: NavController,
            @IdRes resId: Int,
            word: String,
        ) {
            val bundle = Bundle().apply {
                putString(ARG_WORD, word)
            }

            navController.navigate(resId, bundle)
        }
    }
}