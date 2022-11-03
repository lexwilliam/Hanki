package com.lexwilliam.feature_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.feature_add.adapter.FlashcardListAdapter
import com.lexwilliam.feature_add.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val initFlashcard = listOf(Flashcard(question="", answer=""))
        val flashcardListAdapter = FlashcardListAdapter(initFlashcard)
        binding.rvFlashcardList.apply {
            adapter = flashcardListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        return binding.root
    }
}