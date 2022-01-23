package com.lexwilliam.data

import com.lexwilliam.data.model.FlashcardRepositoryModel
import com.lexwilliam.data.model.StudySetRepositoryModel
import com.lexwilliam.domain.model.StudySet
import kotlinx.coroutines.flow.Flow

interface FlashcardLocalSource {

    fun getAllStudySetWithFlashcard(): Flow<List<StudySetRepositoryModel>>

    fun getStudySetWithFlashcardById(id: Long): Flow<StudySetRepositoryModel>

    suspend fun insertStudySet(studySet: StudySetRepositoryModel): Long

    suspend fun updateStudySet(studySet: StudySetRepositoryModel): Int

    suspend fun deleteStudySet(studySet: StudySetRepositoryModel): Int

    suspend fun insertFlashcard(flashcard: FlashcardRepositoryModel): Long

    suspend fun updateFlashcard(flashcard: FlashcardRepositoryModel): Int

    suspend fun deleteFlashcard(flashcard: FlashcardRepositoryModel): Int

}