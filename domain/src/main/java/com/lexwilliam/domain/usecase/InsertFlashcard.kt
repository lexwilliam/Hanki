package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.Flashcard
import javax.inject.Inject

interface InsertFlashcard {
    suspend fun execute(
        flashcard: Flashcard
    ): Long
}

class InsertFlashcardImpl @Inject constructor(
    private val flashcardRepository: FlashcardRepository
): InsertFlashcard {
    override suspend fun execute(
        flashcard: Flashcard
    ): Long {
        return flashcardRepository.insertFlashcard(flashcard)
    }
}