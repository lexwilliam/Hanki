package com.lexwilliam.feature_pack_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lexwilliam.feature_pack_detail.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch
import com.lexwilliam.domain.model.Result
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

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
                        binding.textView.text = pack.toString()
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