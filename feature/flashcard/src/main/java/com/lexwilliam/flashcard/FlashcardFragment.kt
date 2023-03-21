package com.lexwilliam.flashcard

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.lexwilliam.core.model.FlashcardPresentation
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.core.model.TestResultPresentation
import com.lexwilliam.domain.model.Result
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
    private var testResult: TestResultPresentation = TestResultPresentation("", mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlashcardBinding.inflate(inflater, container, false)

        val argument = arguments?.getString("packId")

        argument?.let {
            viewModel.getPack(it)
        }

        fun know(question: String, answer: String, packId: String){
            var notExist = true
            testResult.wordList.forEach { word ->
                if (word.question == question) {
                    word.correct += 1
                    notExist = false
                }
            }
            if (notExist) {
                testResult.wordList.add(
                    TestResultPresentation.Word(
                        question = question,
                        answer = answer,
                        correct = 1,
                        incorrect = 0,
                        packIdList = mutableListOf(packId)
                    )
                )
            }
        }

        fun learn(question: String, answer: String, packId: String) {
            var notExist = true
            testResult.wordList.forEach { word ->
                if (word.question == question) {
                    word.incorrect += 1
                    notExist = false
                }
            }
            if (notExist) {
                testResult.wordList.add(
                    TestResultPresentation.Word(
                        question = question,
                        answer = answer,
                        correct = 0,
                        incorrect = 1,
                        packIdList = mutableListOf(packId)
                    )
                )
            }
        }

        fun initFlashcard(pack: PackPresentation) {
            val flashcardList = pack.flashcards.shuffled()
            val flashcardQueue: Queue<FlashcardPresentation> = LinkedList(flashcardList)
            binding.flashcard =
                FlashcardModel(
                    count = (flashcardList.size - (flashcardQueue.size - 1)).toString(),
                    total = flashcardList.size.toString()
                )
            binding.questionText.text = flashcardQueue.peek()?.question ?: ""
            binding.answerText.text = flashcardQueue.peek()?.answer ?: ""
            binding.knowBtn.setOnClickListener {
                know(flashcardQueue.peek()!!.question, flashcardQueue.peek()!!.answer, pack.id)
                Timber.d(testResult.toString())
                flashcardQueue.remove()
                if (flashcardQueue.isNotEmpty()) {
                    binding.flashcard =
                        FlashcardModel(
                            count = (flashcardList.size - (flashcardQueue.size - 1)).toString(),
                            total = flashcardList.size.toString()
                        )
                    binding.questionText.text = flashcardQueue.peek()?.question ?: ""
                    binding.answerText.text = flashcardQueue.peek()?.answer ?: ""
                } else {
                    Timber.d(testResult.toString())
                    viewModel.insertTestResult(testResult)
                    val request = NavDeepLinkRequest.Builder
                        .fromUri("android-app://lexwilliam.hanki.app/result_fragment/${pack.id}".toUri())
                        .build()
                    findNavController().navigate(request)
                }
            }
            binding.learningBtn.setOnClickListener {
                learn(flashcardQueue.peek()!!.question, flashcardQueue.peek()!!.answer, pack.id)
                Timber.d(testResult.toString())
                val temp = flashcardQueue.peek()
                flashcardQueue.remove()
                flashcardQueue.add(temp)
                binding.questionText.text = flashcardQueue.peek()?.question ?: ""
                binding.answerText.text = flashcardQueue.peek()?.answer ?: ""
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (val pack = state.pack) {
                    is Result.Success -> {
                        Timber.d("Success")
                        initFlashcard(pack.data)
                    }
                    is Result.Loading -> {
                        Timber.d("Loading Pack")
                    }
                    is Result.Error -> {
                        Timber.d("Error")
                    }
                }
            }
        }

        fun flipFlashcard() {
            val scale = requireContext().resources.displayMetrics.density
            binding.question.cameraDistance = 8000 * scale
            binding.answer.cameraDistance = 8000 * scale
            frontAnim =
                AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
            backAnim =
                AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet
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


        binding.flashcardContainer.setOnClickListener {
            flipFlashcard()
        }

        binding.flipBtn.setOnClickListener {
            flipFlashcard()
        }

        return binding.root
    }
}