package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.StudySet
import javax.inject.Inject

interface UpdateStudySet {
    suspend fun execute(
        studySet: StudySet
    ): Int
}

class UpdateStudySetImpl @Inject constructor(
    private val flashcardRepository: FlashcardRepository
): UpdateStudySet {
    override suspend fun execute(
        studySet: StudySet
    ): Int {
        return flashcardRepository.updateStudySet(studySet)
    }
}