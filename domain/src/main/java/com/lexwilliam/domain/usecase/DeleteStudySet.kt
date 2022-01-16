package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.StudySet
import javax.inject.Inject

interface DeleteStudySet {
    suspend fun execute(
        studySet: StudySet
    ): Int
}

class DeleteStudySetImpl @Inject constructor(
    private val flashcardRepository: FlashcardRepository
): DeleteStudySet {
    override suspend fun execute(
        studySet: StudySet
    ): Int {
        return flashcardRepository.deleteStudySet(studySet)
    }
}