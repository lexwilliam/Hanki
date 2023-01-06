package com.lexwilliam.feature_packs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexwilliam.core.util.ItemOffsetDecoration
import com.lexwilliam.domain.model.Result
import com.lexwilliam.feature_packs.adapter.PackListAdapter
import com.lexwilliam.feature_packs.databinding.FragmentPacksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PacksFragment : Fragment() {

    private lateinit var binding: FragmentPacksBinding
    private val viewModel: PacksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPacksBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://lexwilliam.hanki.app/add_fragment".toUri())
                .build()
            findNavController().navigate(request)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(val user = state.user) {
                    is Result.Loading -> Timber.d("Loading")
                    is Result.Success -> {
                        Timber.d("Success")
                        val historyAdapter = PackListAdapter(user.data.packs)
                        binding.packsList.apply {
                            adapter = historyAdapter
                            layoutManager = GridLayoutManager(requireContext(), 2)
                            addItemDecoration(ItemOffsetDecoration(32, true))
                        }
                    }
                    is Result.Error -> Timber.e(user.message)
                }
            }
        }

        return binding.root
    }
}