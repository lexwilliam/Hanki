package com.lexwilliam.hanki.model

data class FlashcardPresentation(
    val id: Long,
    val studySetName: String,
    val question: String,
    val answer: String
)