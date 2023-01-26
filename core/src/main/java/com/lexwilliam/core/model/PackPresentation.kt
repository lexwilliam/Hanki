package com.lexwilliam.core.model

import android.net.Uri
import com.lexwilliam.domain.model.Flashcard
import java.util.*

data class PackPresentation(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val pictureUrl: String?,
    val creatorName: String,
    val creatorPhotoUrl: String?,
    val flashcards: List<FlashcardPresentation>
)