package com.lexwilliam.feature_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexwilliam.feature_add.adapter.FlashcardListAdapter
import com.lexwilliam.feature_add.adapter.HeaderAdapter
import com.lexwilliam.feature_add.databinding.FragmentAddBinding
import com.lexwilliam.core.model.FlashcardPresentation
import com.lexwilliam.core.model.TitlePresentation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val flashcards = ArrayList<FlashcardPresentation>()
    private val title = TitlePresentation("")
    private val flashcardListAdapter by lazy { FlashcardListAdapter() }
    private val headerAdapter by lazy { HeaderAdapter(title) }
    private val viewModel: AddViewModel by viewModels()
    private var count = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        setToolbar()
        setAdapter()

        return binding.root
    }

    private fun setToolbar() {
        binding.addToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.addToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.save -> {
                    viewModel.createPack(
                        title = title.title,
                        flashcards = flashcards
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setAdapter() {
        val concatAdapter = ConcatAdapter(headerAdapter, flashcardListAdapter)

        binding.rvFlashcardList.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        flashcards.add(FlashcardPresentation())
        flashcardListAdapter.setData(flashcards)

        binding.fabAddFlashcard.setOnClickListener {
            flashcards.add(FlashcardPresentation())
            flashcardListAdapter.setData(flashcards)
            // scroll to added item
            count++
            binding.rvFlashcardList.scrollToPosition(count)
        }
    }
}