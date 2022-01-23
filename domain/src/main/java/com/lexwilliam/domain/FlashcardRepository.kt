package com.lexwilliam.domain

import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.StudySet
import kotlinx.coroutines.flow.Flow

interface FlashcardRepository {

    fun getAllStudySetWithFlashcard(): Flow<List<StudySet>>

    fun getStudySetWithFlashcardById(id: Long): Flow<StudySet>

    suspend fun insertStudySet(studySet: StudySet): Long

    suspend fun updateStudySet(studySet: StudySet): Int

    suspend fun deleteStudySet(studySet: StudySet): Int

    suspend fun insertFlashcard(flashcard: Flashcard): Long

    suspend fun updateFlashcard(flashcard: Flashcard): Int

    suspend fun deleteFlashcard(flashcard: Flashcard): Int

}