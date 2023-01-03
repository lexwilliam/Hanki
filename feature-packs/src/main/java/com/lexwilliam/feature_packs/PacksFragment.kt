package com.lexwilliam.feature_packs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.lexwilliam.feature_packs.databinding.FragmentPacksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PacksFragment : Fragment() {

    private lateinit var binding: FragmentPacksBinding

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

        return binding.root
    }
}