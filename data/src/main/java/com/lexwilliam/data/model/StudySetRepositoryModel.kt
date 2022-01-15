package com.lexwilliam.data.model

data class StudySetRepositoryModel(
    val id: Long,
    val name: String,
    val totalFlashcard: Int,
    val flashcards: List<FlashcardRepositoryModel>
)
