package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.StudySet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetStudySetById {
    suspend fun execute(
        id: Long
    ): StudySet?
}

class GetStudySetByIdImpl @Inject constructor(
    private val flashcardRepository: FlashcardRepository
): GetStudySetById {
    override suspend fun execute(
        id: Long
    ): StudySet? {
        return flashcardRepository.getStudySetWithFlashcardById(id)
    }
}