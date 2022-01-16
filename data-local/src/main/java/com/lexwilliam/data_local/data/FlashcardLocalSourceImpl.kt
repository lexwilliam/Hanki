package com.lexwilliam.data_local.data

import com.lexwilliam.data.FlashcardLocalSource
import com.lexwilliam.data.model.FlashcardRepositoryModel
import com.lexwilliam.data.model.StudySetRepositoryModel
import com.lexwilliam.data_local.dao.FlashcardDao
import com.lexwilliam.data_local.dao.StudySetDao
import com.lexwilliam.data_local.mapper.FlashcardMapper
import com.lexwilliam.data_local.mapper.StudySetMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FlashcardLocalSourceImpl @Inject constructor(
    private val studySetDao: StudySetDao,
    private val flashcardDao: FlashcardDao,
    private val studySetMapper: StudySetMapper,
    private val flashcardMapper: FlashcardMapper
): FlashcardLocalSource {

    override fun getAllStudySetWithFlashcard(): Flow<List<StudySetRepositoryModel>> {
        return studySetDao.getAllStudySetWithFlashcard().map { studySetMapper.toRepo(it) }
    }

    override suspend fun getStudySetWithFlashcardById(id: Long): StudySetRepositoryModel? {
        return studySetDao.getStudySetWithFlashcardById(id)?.let { studySetMapper.toRepo(it) }
    }

    override suspend fun insertStudySet(studySet: StudySetRepositoryModel): Long {
        return studySetDao.insertStudySet(studySetMapper.toEntity(studySet))
    }

    override suspend fun updateStudySet(studySet: StudySetRepositoryModel): Int {
        return studySetDao.updateStudySet(studySetMapper.toEntity(studySet))
    }

    override suspend fun deleteStudySet(studySet: StudySetRepositoryModel): Int {
        return studySetDao.deleteStudySet(studySetMapper.toEntity(studySet))
    }

    override suspend fun insertFlashcard(flashcard: FlashcardRepositoryModel): Long {
        return flashcardDao.insertFlashcard(flashcardMapper.toEntity(flashcard))
    }

    override suspend fun updateFlashcard(flashcard: FlashcardRepositoryModel): Int {
        return flashcardDao.updateFlashcard(flashcardMapper.toEntity(flashcard))
    }

    override suspend fun deleteFlashcard(flashcard: FlashcardRepositoryModel): Int {
        return flashcardDao.deleteFlashcard(flashcardMapper.toEntity(flashcard))
    }

    private fun getInitialStateStudySetModel() = StudySetRepositoryModel(
        -1, "", -1, emptyList()
    )

    private fun getInitialStateFlashcardModel() = FlashcardRepositoryModel(
        -1, "", "", ""
    )
}