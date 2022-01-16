package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.StudySet
import javax.inject.Inject

interface DeleteFlashcard {
    suspend fun execute(
        flashcard: Flashcard
    ): Int
}

class DeleteFlashcardImpl @Inject constructor(
    private val flashcardRepository: FlashcardRepository
): DeleteFlashcard {
    override suspend fun execute(
        flashcard: Flashcard
    ): Int {
        return flashcardRepository.deleteFlashcard(flashcard)
    }
}