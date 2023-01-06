package com.lexwilliam.data.model

import android.net.Uri
import com.lexwilliam.domain.model.Flashcard
import java.util.*

data class PackResponse(
    val id: String,
    val title: String?,
    val creatorName: String?,
    val creatorPhotoUrl: String?,
    val flashcards: List<FlashcardResponse>?
)