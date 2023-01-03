package com.lexwilliam.feature_add.model

import android.net.Uri
import com.lexwilliam.domain.model.Flashcard
import java.util.*

data class PackPresentation(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val creatorName: String,
    val creatorPhotoUrl: Uri?,
    val flashcards: List<FlashcardPresentation>
)