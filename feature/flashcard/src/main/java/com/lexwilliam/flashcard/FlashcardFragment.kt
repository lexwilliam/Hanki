package com.lexwilliam.flashcard

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.viewModels
import com.lexwilliam.domain.model.Result
import androidx.lifecycle.lifecycleScope
import com.lexwilliam.core.model.FlashcardPresentation
import com.lexwilliam.flashcard.R
import com.lexwilliam.flashcard.databinding.FragmentFlashcardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class FlashcardFragment : Fragment() {

    private lateinit var binding: FragmentFlashcardBinding
    private val viewModel: FlashcardViewModel by viewModels()
    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet
    private var isFront = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlashcardBinding.inflate(inflater, container, false)

        val argument = arguments?.getString("packId")

        argument?.let {
            viewModel.getPack(it)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (val pack = state.pack) {
                    is Result.Success -> {
                        val flashcardList = pack.data.flashcards.shuffled()
                        val flashcardQueue: Queue<FlashcardPresentation> = LinkedList(flashcardList)
                        binding.flashcard =
                                FlashcardModel(
                                    count = (flashcardList.size - (flashcardQueue.size - 1)).toString(),
                                    total = flashcardList.size.toString()
                                )
                        binding.questionText.text = flashcardQueue.peek()?.question ?: ""
                        binding.answerText.text = flashcardQueue.peek()?.answer ?: ""
                        binding.knowBtn.setOnClickListener {
                            flashcardQueue.remove()
                            if (flashcardQueue.isNotEmpty()) {
                                binding.flashcard =
                                    FlashcardModel(
                                        count = (flashcardList.size - (flashcardQueue.size - 1)).toString(),
                                        total = flashcardList.size.toString()
                                    )
                                binding.questionText.text = flashcardQueue.peek()?.question ?: ""
                                binding.answerText.text = flashcardQueue.peek()?.answer ?: ""
                            }
                        }
                        binding.learningBtn.setOnClickListener {
                            val temp = flashcardQueue.peek()
                            flashcardQueue.remove()
                            flashcardQueue.add(temp)
                            binding.questionText.text = flashcardQueue.peek()?.question ?: ""
                            binding.answerText.text = flashcardQueue.peek()?.answer ?: ""
                        }
                    }
                    is Result.Loading -> {
                        Timber.d("Loading")
                    }
                    is Result.Error -> {
                        Timber.d("Error")
                    }
                }
            }
        }

        val scale = requireContext().resources.displayMetrics.density
        binding.question.cameraDistance = 8000 * scale
        binding.answer.cameraDistance = 8000 * scale
        frontAnim = AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet

        binding.flashcardContainer.setOnClickListener {
            if (isFront) {
                frontAnim.setTarget(binding.question);
                backAnim.setTarget(binding.answer);
                frontAnim.start()
                backAnim.start()
                isFront = false
            } else {
                frontAnim.setTarget(binding.answer)
                backAnim.setTarget(binding.question)
                backAnim.start()
                frontAnim.start()
                isFront = true
            }
        }

        binding.flipBtn.setOnClickListener {
            if (isFront) {
                frontAnim.setTarget(binding.question);
                backAnim.setTarget(binding.answer);
                frontAnim.start()
                backAnim.start()
                isFront = false
            } else {
                frontAnim.setTarget(binding.answer)
                backAnim.setTarget(binding.question)
                backAnim.start()
                frontAnim.start()
                isFront = true
            }
        }

        return binding.root
    }
}