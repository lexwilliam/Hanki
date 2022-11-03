package com.lexwilliam.feature_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.feature_add.adapter.FlashcardListAdapter
import com.lexwilliam.feature_add.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val flashcardListAdapter by lazy { FlashcardListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvFlashcardList.apply {
            adapter = flashcardListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val initFlashcard = Flashcard(question = "", answer = "")
        flashcardListAdapter.setData(mutableListOf(initFlashcard))


        return binding.root
    }
}