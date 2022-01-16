package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.StudySet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllStudySets {
    suspend fun execute(): Flow<List<StudySet>>
}

class GetAllStudySetsImpl @Inject constructor(
    private val flashcardRepository: FlashcardRepository
): GetAllStudySets {
    override suspend fun execute(): Flow<List<StudySet>> {
        return flashcardRepository.getAllStudySetWithFlashcard()
    }
}