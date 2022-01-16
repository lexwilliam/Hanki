package com.lexwilliam.data.mapper

import com.lexwilliam.data.model.FlashcardRepositoryModel
import com.lexwilliam.data.model.StudySetRepositoryModel
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.StudySet

internal fun StudySet.toRepo(): StudySetRepositoryModel {
    return StudySetRepositoryModel(
        id, name, totalFlashcard, flashcards.map { it.toRepo() }
    )
}

internal fun Flashcard.toRepo(): FlashcardRepositoryModel {
    return FlashcardRepositoryModel(
        id, studySetName, question, answer
    )
}