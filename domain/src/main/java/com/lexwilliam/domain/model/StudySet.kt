package com.lexwilliam.domain.model

data class StudySet(
    val id: Long,
    val name: String,
    val totalFlashcard: Int,
    val flashcards: List<Flashcard>
)