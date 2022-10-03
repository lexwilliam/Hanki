package com.lexwilliam.domain.model

import android.net.Uri

data class Pack(
    val id: Long,
    val name: String,
    val creatorName: String,
    val creatorPhotoUrl: String,
    val flashcards: List<Flashcard>
)