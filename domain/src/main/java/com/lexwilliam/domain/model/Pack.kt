package com.lexwilliam.domain.model

import android.net.Uri
import java.util.*

data class Pack(
    val id: String?,
    val title: String?,
    val creatorName: String?,
    val creatorPhotoUrl: String?,
    val flashcards: List<Flashcard>?
)