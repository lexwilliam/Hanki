package com.lexwilliam.feature_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.lexwilliam.domain.model.Result
import com.lexwilliam.feature_home.adapter.HistoryAdapter
import com.lexwilliam.feature_home.databinding.FragmentHomeBinding
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
                when(val userPackList = state.userPackList) {
                    is Result.Loading -> Timber.d("Loading")
                    is Result.Success -> {
                        Timber.d("Success")
                        val historyAdapter = HistoryAdapter(userPackList.data)
                        binding.rvMyPacks.apply {
                            adapter = historyAdapter
                            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        }
                    }
                    is Result.Error -> Timber.e(userPackList.message)
                }
                when(val user = state.user) {
                    is Result.Loading -> Timber.d("Loading")
                    is Result.Success -> {
                        Timber.d("Success")
                        binding.homeGreeting.text = "Hi, ${user.data.name}"
                        binding.profileImage.load(user.data.photoUrl)
                    }
                    is Result.Error -> Timber.e(user.message)
                }
            }
        }

        return binding.root
    }
}