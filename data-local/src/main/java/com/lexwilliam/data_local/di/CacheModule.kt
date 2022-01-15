package com.lexwilliam.data_local.di

import android.content.Context
import androidx.room.Room
import com.lexwilliam.data_local.FlashcardDatabase
import com.lexwilliam.data_local.dao.FlashcardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideReportDb(@ApplicationContext context : Context): FlashcardDatabase {
        return Room
            .databaseBuilder(
                context,
                FlashcardDatabase::class.java,
                "flashcard_database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideReportDAO(flashcardDatabase: FlashcardDatabase): FlashcardDao {
        return flashcardDatabase.flashcardDao()
    }

}