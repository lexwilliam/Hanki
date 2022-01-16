package com.lexwilliam.data.mapper

import com.lexwilliam.data.model.FlashcardRepositoryModel
import com.lexwilliam.domain.model.Flashcard
import javax.inject.Inject

interface RepositoryFlashcardMapper {
    fun toDomain(flashcard: FlashcardRepositoryModel): Flashcard
    fun toDomain(flashcardList: List<FlashcardRepositoryModel>): List<Flashcard>
    fun toRepo(flashcard: Flashcard): FlashcardRepositoryModel
    fun toRepo(flashcardList: List<Flashcard>): List<FlashcardRepositoryModel>
}

class RepositoryFlashcardMapperImpl @Inject constructor():
    RepositoryFlashcardMapper {
    override fun toDomain(flashcard: FlashcardRepositoryModel): Flashcard {
        return Flashcard(
            id = flashcard.id,
            studySetName =  flashcard.studySetName,
            question = flashcard.question,
            answer = flashcard.answer
        )
    }

    override fun toDomain(flashcardList: List<FlashcardRepositoryModel>): List<Flashcard> {
        return flashcardList.map{ toDomain(it) }
    }

    override fun toRepo(flashcard: Flashcard): FlashcardRepositoryModel {
        return FlashcardRepositoryModel(
            id = flashcard.id,
            studySetName =  flashcard.studySetName,
            question = flashcard.question,
            answer = flashcard.answer
        )
    }

    override fun toRepo(flashcardList: List<Flashcard>): List<FlashcardRepositoryModel> {
        return flashcardList.map { toRepo(it) }
    }
}