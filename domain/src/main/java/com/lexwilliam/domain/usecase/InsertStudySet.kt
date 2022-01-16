package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.StudySet
import javax.inject.Inject

interface InsertStudySet {
    suspend fun execute(
        studySet: StudySet
    ): Long
}

class InsertStudySetImpl @Inject constructor(
    private val flashcardRepository: FlashcardRepository
): InsertStudySet {
    override suspend fun execute(
        studySet: StudySet
    ): Long {
        return flashcardRepository.insertStudySet(studySet)
    }
}