package com.lexwilliam.data_local.model

import androidx.room.Embedded
import androidx.room.Relation

data class StudySetWithFlashcard(
    @Embedded val studySet: StudySetEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "studySetName"
    )
    val flashcards: List<FlashcardEntity>
)