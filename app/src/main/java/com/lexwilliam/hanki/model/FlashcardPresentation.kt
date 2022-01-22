package com.lexwilliam.hanki.model

data class FlashcardPresentation(
    val id: Long = 0,
    val studySetName: String,
    val question: String,
    val answer: String
)