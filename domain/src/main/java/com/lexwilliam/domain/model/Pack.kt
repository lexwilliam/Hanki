package com.lexwilliam.domain.model

import android.net.Uri
import java.util.*

data class Pack(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val creatorName: String,
    val creatorPhotoUrl: Uri?,
    val flashcards: List<Flashcard>
)