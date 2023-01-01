package com.lexwilliam.feature_add.adapter

import androidx.recyclerview.widget.DiffUtil
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.feature_add.model.FlashcardPresentation

class FlashcardListDiffUtil(
    private val oldList: List<FlashcardPresentation>,
    private val newList: List<FlashcardPresentation>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList == newList
    }

}