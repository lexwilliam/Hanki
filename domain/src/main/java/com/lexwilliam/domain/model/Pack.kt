package com.lexwilliam.domain.model

import java.util.*

data class Pack(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val creatorName: String,
    val creatorPhotoUrl: String,
    val flashcards: List<Flashcard>
)