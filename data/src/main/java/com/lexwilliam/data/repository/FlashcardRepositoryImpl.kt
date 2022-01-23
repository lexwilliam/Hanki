package com.lexwilliam.data.repository

import com.lexwilliam.data.FlashcardLocalSource
import com.lexwilliam.data.mapper.RepositoryFlashcardMapper
import com.lexwilliam.data.mapper.RepositoryStudySetMapper
import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.StudySet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FlashcardRepositoryImpl @Inject constructor(
    private val flashcardLocalSource: FlashcardLocalSource,
    private val repositoryStudySetMapper: RepositoryStudySetMapper,
    private val repositoryFlashcardMapper: RepositoryFlashcardMapper
): FlashcardRepository {
    override fun getAllStudySetWithFlashcard(): Flow<List<StudySet>> {
        return flashcardLocalSource.getAllStudySetWithFlashcard().map { repositoryStudySetMapper.toDomain(it) }
    }

    override fun getStudySetWithFlashcardById(id: Long): Flow<StudySet> {
        return flashcardLocalSource.getStudySetWithFlashcardById(id).map { repositoryStudySetMapper.toDomain(it) }
    }

    override suspend fun insertStudySet(studySet: StudySet): Long {
        return flashcardLocalSource.insertStudySet(repositoryStudySetMapper.toRepo(studySet))
    }

    override suspend fun updateStudySet(studySet: StudySet): Int {
        return flashcardLocalSource.updateStudySet(repositoryStudySetMapper.toRepo(studySet))
    }

    override suspend fun deleteStudySet(studySet: StudySet): Int {
        return flashcardLocalSource.deleteStudySet(repositoryStudySetMapper.toRepo(studySet))
    }

    override suspend fun insertFlashcard(flashcard: Flashcard): Long {
        return flashcardLocalSource.insertFlashcard(repositoryFlashcardMapper.toRepo(flashcard))
    }

    override suspend fun updateFlashcard(flashcard: Flashcard): Int {
        return flashcardLocalSource.updateFlashcard(repositoryFlashcardMapper.toRepo(flashcard))
    }

    override suspend fun deleteFlashcard(flashcard: Flashcard): Int {
        return flashcardLocalSource.deleteFlashcard(repositoryFlashcardMapper.toRepo(flashcard))
    }


}