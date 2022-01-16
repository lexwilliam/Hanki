package com.lexwilliam.data_local.dao

import androidx.room.*
import com.lexwilliam.data_local.model.StudySetEntity
import com.lexwilliam.data_local.model.StudySetWithFlashcard
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySetDao {

    @Query("SELECT * FROM studySet ORDER BY id")
    fun getAllStudySetWithFlashcard(): Flow<List<StudySetWithFlashcard>>

    @Query("SELECT * FROM studySet WHERE id IS :id")
    suspend fun getStudySetWithFlashcardById(id: Long): StudySetWithFlashcard?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudySet(studySet: StudySetEntity): Long

    @Update
    suspend fun updateStudySet(studySet: StudySetEntity): Int

    @Delete
    suspend fun deleteStudySet(studySet: StudySetEntity): Int
}