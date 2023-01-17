package com.lexwilliam.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexwilliam.core.util.ItemOffsetDecoration
import com.lexwilliam.explore.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.lexwilliam.domain.model.Result
import com.lexwilliam.explore.adapter.ExplorePacksAdapter
import timber.log.Timber

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private val viewModel: ExploreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (val packs = state.packs) {
                    is Result.Success -> {
                        binding.recyclerView.apply {
                            adapter = ExplorePacksAdapter(
                                packs = packs.data,
                                onItemClicked = { pack ->
                                    val request = NavDeepLinkRequest.Builder
                                        .fromUri("android-app://lexwilliam.hanki.app/pack_detail_fragment/${pack.id}".toUri())
                                        .build()
                                    findNavController().navigate(request)
                                }
                            )
                            layoutManager = LinearLayoutManager(requireContext())
                            addItemDecoration(ItemOffsetDecoration(64, true))
                        }
                    }
                    is Result.Loading -> {
                        Timber.d("Loading")
                    }
                    is Result.Error -> {
                        Timber.d("Error")
                    }
                }
            }
        }

        return binding.root
    }
}