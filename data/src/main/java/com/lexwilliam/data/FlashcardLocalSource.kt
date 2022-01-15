package com.lexwilliam.data

import com.lexwilliam.data.model.StudySetRepositoryModel
import kotlinx.coroutines.flow.Flow

interface FlashcardLocalSource {

    suspend fun getAllStudySetWithFlashcard(): Flow<List<StudySetRepositoryModel>>

    suspend fun getStudySetWithFlashcardById(id: Long): Flow<StudySetRepositoryModel>

}