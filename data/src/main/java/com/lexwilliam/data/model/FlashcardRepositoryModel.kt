package com.lexwilliam.data.model

data class FlashcardRepositoryModel(
    val id: Long,
    val studySetName: String,
    val question: String,
    val answer: String
)