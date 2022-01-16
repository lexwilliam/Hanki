package com.lexwilliam.hanki.model

data class StudySetPresentation(
    val id: Long,
    val name: String,
    val totalFlashcard: Int,
    val flashcards: List<FlashcardPresentation>
)