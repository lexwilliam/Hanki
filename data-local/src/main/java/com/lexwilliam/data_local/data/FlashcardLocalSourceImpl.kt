package com.lexwilliam.data_local.data

import com.lexwilliam.data.FlashcardLocalSource
import com.lexwilliam.data.model.FlashcardRepositoryModel
import com.lexwilliam.data.model.StudySetRepositoryModel
import com.lexwilliam.data_local.dao.FlashcardDao
import com.lexwilliam.data_local.dao.StudySetDao
import com.lexwilliam.data_local.mapper.LocalFlashcardMapper
import com.lexwilliam.data_local.mapper.LocalStudySetMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FlashcardLocalSourceImpl @Inject constructor(
    private val studySetDao: StudySetDao,
    private val flashcardDao: FlashcardDao,
    private val localStudySetMapper: LocalStudySetMapper,
    private val localFlashcardMapper: LocalFlashcardMapper
): FlashcardLocalSource {

    override fun getAllStudySetWithFlashcard(): Flow<List<StudySetRepositoryModel>> {
        return studySetDao.getAllStudySetWithFlashcard().map { localStudySetMapper.toRepo(it) }
    }

    override fun getStudySetWithFlashcardById(id: Long): Flow<StudySetRepositoryModel> {
        return studySetDao.getStudySetWithFlashcardById(id).map { localStudySetMapper.toRepo(it) }
    }

    override suspend fun insertStudySet(studySet: StudySetRepositoryModel): Long {
        return studySetDao.insertStudySet(localStudySetMapper.toEntity(studySet))
    }

    override suspend fun updateStudySet(studySet: StudySetRepositoryModel): Int {
        return studySetDao.updateStudySet(localStudySetMapper.toEntity(studySet))
    }

    override suspend fun deleteStudySet(studySet: StudySetRepositoryModel): Int {
        return studySetDao.deleteStudySet(localStudySetMapper.toEntity(studySet))
    }

    override suspend fun insertFlashcard(flashcard: FlashcardRepositoryModel): Long {
        return flashcardDao.insertFlashcard(localFlashcardMapper.toEntity(flashcard))
    }

    override suspend fun updateFlashcard(flashcard: FlashcardRepositoryModel): Int {
        return flashcardDao.updateFlashcard(localFlashcardMapper.toEntity(flashcard))
    }

    override suspend fun deleteFlashcard(flashcard: FlashcardRepositoryModel): Int {
        return flashcardDao.deleteFlashcard(localFlashcardMapper.toEntity(flashcard))
    }

}