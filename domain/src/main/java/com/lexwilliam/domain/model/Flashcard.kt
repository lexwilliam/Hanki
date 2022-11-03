package com.lexwilliam.domain.model

import java.util.*

data class Flashcard(
    val id: String = UUID.randomUUID().toString(),
    val question: String,
    val answer: String
)