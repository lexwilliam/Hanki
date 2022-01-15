package com.lexwilliam.data_local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studySet")
data class StudySetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val totalFlashcard: Int = 0
)