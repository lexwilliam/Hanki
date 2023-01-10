package com.lexwilliam.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.core.model.FlashcardPresentation
import com.lexwilliam.detail.databinding.FlashcardCardBinding

class FlashcardListAdapter(
    private val flashcards: List<FlashcardPresentation>
): RecyclerView.Adapter<FlashcardListAdapter.FlashcardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder =
        FlashcardViewHolder(
            FlashcardCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        holder.bind(flashcards[position])
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }


    inner class FlashcardViewHolder(private val binding: FlashcardCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(flashcard: FlashcardPresentation) {
            binding.flashcard = flashcard
        }
    }
}