package com.lexwilliam.domain

import com.lexwilliam.domain.model.StudySet
import kotlinx.coroutines.flow.Flow

interface FlashcardRepository {
    suspend fun getAllStudySetWithFlashcard(): Flow<List<StudySet>>

    suspend fun getStudySetWithFlashcardById(id: Long): Flow<StudySet>
}