package com.lexwilliam.feature_add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.feature_add.databinding.FlashcardEditCardBinding
import com.lexwilliam.core.model.FlashcardPresentation


class FlashcardEditAdapter:
    RecyclerView.Adapter<FlashcardEditAdapter.FlashcardViewHolder>() {

    private val flashcards = ArrayList<FlashcardPresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder =
        FlashcardViewHolder(
            FlashcardEditCardBinding.inflate(
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

    inner class FlashcardViewHolder(private val binding: FlashcardEditCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(flashcard: FlashcardPresentation) {
            binding.flashcard = flashcard
        }
    }

    fun setData(newList: ArrayList<FlashcardPresentation>) {
        val diffUtil = FlashcardListDiffUtil(flashcards, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        flashcards.clear()
        flashcards.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}