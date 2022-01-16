package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.Flashcard
import javax.inject.Inject

interface UpdateFlashcard {
    suspend fun execute(
        flashcard: Flashcard
    ): Int
}

class UpdateFlashcardImpl @Inject constructor(
    private val flashcardRepository: FlashcardRepository
): UpdateFlashcard {
    override suspend fun execute(
        flashcard: Flashcard
    ): Int {
        return flashcardRepository.updateFlashcard(flashcard)
    }
}