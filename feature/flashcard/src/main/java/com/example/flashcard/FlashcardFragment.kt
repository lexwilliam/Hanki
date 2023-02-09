package com.example.flashcard

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import com.lexwilliam.flashcard.R
import com.lexwilliam.flashcard.databinding.FragmentFlashcardBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FlashcardFragment : Fragment() {

    private lateinit var binding: FragmentFlashcardBinding
    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet
    private var isFront = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlashcardBinding.inflate(inflater, container, false)

        var scale = requireContext().resources.displayMetrics.density
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
                isFront =true
            }

        }



        return binding.root
    }
}