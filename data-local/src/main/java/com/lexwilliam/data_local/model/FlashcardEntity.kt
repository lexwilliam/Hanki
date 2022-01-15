package com.lexwilliam.data_local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcard")
data class FlashcardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val studySetName: String,
    val question: String,
    val answer: String
)