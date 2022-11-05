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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.feature_add.adapter.FlashcardListAdapter
import com.lexwilliam.feature_add.adapter.HeaderAdapter
import com.lexwilliam.feature_add.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val flashcardListAdapter by lazy { FlashcardListAdapter() }
    private val headerAdapter by lazy { HeaderAdapter() }
    private var count = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val concatAdapter = ConcatAdapter(headerAdapter, flashcardListAdapter)

        binding.rvFlashcardList.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val flashcards = ArrayList<Flashcard>()
        flashcards.add(Flashcard(question = "", answer = ""))
        flashcardListAdapter.setData(flashcards)

        binding.fabAddFlashcard.setOnClickListener {
            flashcards.add(Flashcard(question = "", answer = ""))
            flashcardListAdapter.setData(flashcards)

            // scroll to added item
            count++
            binding.rvFlashcardList.scrollToPosition(count)
        }


        return binding.root
    }
}
