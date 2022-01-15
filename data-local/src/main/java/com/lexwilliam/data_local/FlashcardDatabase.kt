package com.lexwilliam.data_local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lexwilliam.data_local.dao.FlashcardDao
import com.lexwilliam.data_local.model.FlashcardEntity
import com.lexwilliam.data_local.model.StudySetEntity

@Database(entities = [StudySetEntity::class, FlashcardEntity::class], version = 1, exportSchema = false)
abstract class FlashcardDatabase: RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao
}