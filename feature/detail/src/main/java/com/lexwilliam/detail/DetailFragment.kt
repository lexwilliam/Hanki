package com.lexwilliam.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexwilliam.core.util.ItemOffsetDecoration
import com.lexwilliam.detail.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch
import com.lexwilliam.domain.model.Result
import com.lexwilliam.detail.adapter.FlashcardListAdapter
import com.lexwilliam.detail.adapter.HeaderDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var headerAdapter: HeaderDetailAdapter
    private lateinit var flashcardListAdapter: FlashcardListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val argument = arguments?.getString("packId")

        argument?.let {
            viewModel.getPack(it)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (val pack = state.pack) {
                    is Result.Success -> {
                        headerAdapter = HeaderDetailAdapter(
                            pack = pack.data,
                            onFlashcardClick = {
                                val request = NavDeepLinkRequest.Builder
                                    .fromUri("android-app://lexwilliam.hanki.app/flashcard_fragment/${it.id}".toUri())
                                    .build()
                                findNavController().navigate(request)
                            }
                        )
                        flashcardListAdapter = FlashcardListAdapter(pack.data.flashcards)
                        val concatAdapter = ConcatAdapter(headerAdapter, flashcardListAdapter)
                        binding.recyclerView.apply {
                            adapter = concatAdapter
                            layoutManager = LinearLayoutManager(requireContext())
                            addItemDecoration(ItemOffsetDecoration(32, false))
                        }
                    }
                    is Result.Loading -> {
                        Timber.d("Loading")
                    }
                    is Result.Error -> {
                        Timber.d("Error: ${pack.message}")
                    }
                }
            }
        }

        return binding.root
    }
}