package com.lexwilliam.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.lexwilliam.core.model.UserPresentation
import com.lexwilliam.core.util.ItemOffsetDecoration
import com.lexwilliam.domain.model.Result
import com.lexwilliam.home.adapter.HistoryAdapter
import com.lexwilliam.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeDate.text = DateTime.now().toString(DateTimeFormat.forPattern("dd MMM"))

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(val user = state.user) {
                    is Result.Success -> {
                        Timber.d("Success")
                        val historyAdapter = HistoryAdapter(
                            packs = user.data.packs,
                            onItemClicked = { packInfo ->
                                val request = NavDeepLinkRequest.Builder
                                    .fromUri("android-app://lexwilliam.hanki.app/pack_detail_fragment/${packInfo.id}".toUri())
                                    .build()
                                findNavController().navigate(request)
                            }
                        )
                        binding.rvMyPacks.apply {
                            adapter = historyAdapter
                            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            addItemDecoration(ItemOffsetDecoration(64, true))
                        }
                        binding.user = user.data
                        binding.profileImage.load(user.data.photoUrl)
                    }
                    is Result.Loading -> Timber.d("Loading")
                    is Result.Error -> Timber.e(user.message)
                }
            }
        }

        return binding.root
    }
}