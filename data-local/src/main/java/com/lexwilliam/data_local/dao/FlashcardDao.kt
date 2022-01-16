package com.lexwilliam.data_local.dao

import androidx.room.*
import com.lexwilliam.data_local.model.FlashcardEntity
import com.lexwilliam.data_local.model.StudySetEntity
import com.lexwilliam.data_local.model.StudySetWithFlashcard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: FlashcardEntity): Long

    @Update
    suspend fun updateFlashcard(flashcard: FlashcardEntity): Int

    @Delete
    suspend fun deleteFlashcard(flashcard: FlashcardEntity): Int
}