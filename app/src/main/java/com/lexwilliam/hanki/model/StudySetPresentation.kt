package com.lexwilliam.hanki.model

data class StudySetPresentation(
    val id: Long = 0,
    val name: String,
    val totalFlashcard: Int,
    val flashcards: List<FlashcardPresentation>
)