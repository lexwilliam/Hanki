package com.lexwilliam.data.model

import android.net.Uri
import com.lexwilliam.domain.model.Flashcard
import java.util.*

data class PackResponse(
    val id: String? = null,
    val title: String? = null,
    val pictureUrl: String? = null,
    val creatorName: String? = null,
    val creatorPhotoUrl: String? = null,
    val flashcards: List<FlashcardResponse>? = null
)