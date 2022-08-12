package com.lexwilliam.feature_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexwilliam.domain.model.Result
import com.lexwilliam.feature_home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
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

        val state = viewModel.state.value

        binding.homeDate.text = DateTime.now().toString(DateTimeFormat.forPattern("dd MMM"))
        binding.homeGreeting.setOnClickListener {
            viewModel.insertTest()
        }

        when(val tests = state.tests) {
            is Result.Success -> {
                Timber.d("Success")
                val testAdapter = TestAdapter(tests.data)
                binding.rvMyPacks.apply {
                    adapter = testAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
            is Result.Error -> Timber.e(tests.message)
        }


        return binding.root
    }
}