package com.lexwilliam.data

import com.lexwilliam.data.model.FlashcardRepositoryModel
import com.lexwilliam.data.model.StudySetRepositoryModel
import kotlinx.coroutines.flow.Flow

interface FlashcardLocalSource {

    fun getAllStudySetWithFlashcard(): Flow<List<StudySetRepositoryModel>>

    suspend fun getStudySetWithFlashcardById(id: Long): StudySetRepositoryModel?

    suspend fun insertStudySet(studySet: StudySetRepositoryModel): Long

    suspend fun updateStudySet(studySet: StudySetRepositoryModel): Int

    suspend fun deleteStudySet(studySet: StudySetRepositoryModel): Int

    suspend fun insertFlashcard(flashcard: FlashcardRepositoryModel): Long

    suspend fun updateFlashcard(flashcard: FlashcardRepositoryModel): Int

    suspend fun deleteFlashcard(flashcard: FlashcardRepositoryModel): Int

}