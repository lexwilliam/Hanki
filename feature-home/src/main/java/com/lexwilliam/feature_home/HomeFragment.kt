package com.lexwilliam.feature_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.lexwilliam.domain.model.Result
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

        binding.homeGreeting.setOnClickListener {
            viewModel.insertTest()
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(val tests = state.tests) {
                    is Result.Loading -> Timber.d("Test Loading")
                    is Result.Success -> {
                        Timber.d("Success")
                        val testAdapter = TestAdapter(tests.data)
                        binding.rvMyPacks.apply {
                            adapter = testAdapter
                            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        }
                    }
                    is Result.Error -> Timber.e(tests.message)
                }
                when(val user = state.user) {
                    is Result.Loading -> Timber.d("User Loading")
                    is Result.Success -> {
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