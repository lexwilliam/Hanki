package com.lexwilliam.domain.model

data class Flashcard(
    val id: Long,
    val studySetName: String,
    val question: String,
    val answer: String
)