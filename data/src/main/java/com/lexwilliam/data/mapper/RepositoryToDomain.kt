package com.lexwilliam.data.mapper

import com.lexwilliam.data.model.FlashcardRepositoryModel
import com.lexwilliam.data.model.StudySetRepositoryModel
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.StudySet

internal fun StudySetRepositoryModel.toDomain(): StudySet {
    return StudySet(
        id, name, totalFlashcard, flashcards.map{ it.toDomain() }
    )
}

internal fun FlashcardRepositoryModel.toDomain(): Flashcard {
    return Flashcard(
        id, studySetName, question, answer
    )
}