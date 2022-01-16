package com.lexwilliam.hanki.presentation.viewmodel.mapper

import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.hanki.model.FlashcardPresentation
import javax.inject.Inject

interface FlashcardMapperPresentation {
    fun toDomain(flashcard: FlashcardPresentation): Flashcard
    fun toDomain(flashcardList: List<FlashcardPresentation>): List<Flashcard>
    fun toPresentation(flashcard: Flashcard): FlashcardPresentation
    fun toPresentation(flashcardList: List<Flashcard>): List<FlashcardPresentation>
}

class FlashcardMapperPresentationImpl @Inject constructor():
    FlashcardMapperPresentation {
    override fun toDomain(flashcard: FlashcardPresentation): Flashcard {
        return Flashcard(
            id = flashcard.id,
            studySetName =  flashcard.studySetName,
            question = flashcard.question,
            answer = flashcard.answer
        )
    }

    override fun toDomain(flashcardList: List<FlashcardPresentation>): List<Flashcard> {
        return flashcardList.map{ toDomain(it) }
    }

    override fun toPresentation(flashcard: Flashcard): FlashcardPresentation {
        return FlashcardPresentation(
            id = flashcard.id,
            studySetName =  flashcard.studySetName,
            question = flashcard.question,
            answer = flashcard.answer
        )
    }

    override fun toPresentation(flashcardList: List<Flashcard>): List<FlashcardPresentation> {
        return flashcardList.map { toPresentation(it) }
    }
}