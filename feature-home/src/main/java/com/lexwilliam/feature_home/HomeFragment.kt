package com.lexwilliam.feature_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lexwilliam.feature_home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeDate.text = DateTime.now().toString(DateTimeFormat.forPattern("dd MMM"))
        binding.homeGreeting.setOnClickListener {
            viewModel.insertTest()
        }

        return binding.root
    }
}