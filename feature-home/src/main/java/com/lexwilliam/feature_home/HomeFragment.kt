package com.lexwilliam.feature_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lexwilliam.feature_home.databinding.FragmentHomeBinding
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeDate.text = DateTime.now().toString(DateTimeFormat.forPattern("dd MMM"))

        return binding.root
    }
}