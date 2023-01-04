package com.lexwilliam.core.model

import java.util.*

data class FlashcardPresentation(
    var id: String = UUID.randomUUID().toString(),
    var question: String = "",
    var answer: String = ""
)