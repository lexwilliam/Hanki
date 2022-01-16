package com.lexwilliam.data_local.di

import android.content.Context
import androidx.room.Room
import com.lexwilliam.data.FlashcardLocalSource
import com.lexwilliam.data_local.FlashcardDatabase
import com.lexwilliam.data_local.dao.FlashcardDao
import com.lexwilliam.data_local.dao.StudySetDao
import com.lexwilliam.data_local.data.FlashcardLocalSourceImpl
import com.lexwilliam.data_local.mapper.LocalFlashcardMapper
import com.lexwilliam.data_local.mapper.LocalFlashcardMapperImpl
import com.lexwilliam.data_local.mapper.LocalStudySetMapper
import com.lexwilliam.data_local.mapper.LocalStudySetMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideFlashcardLocalSource(
        studySetDao: StudySetDao,
        flashcardDao: FlashcardDao,
        localStudySetMapper: LocalStudySetMapper,
        localFlashcardMapper: LocalFlashcardMapper
    ): FlashcardLocalSource =
        FlashcardLocalSourceImpl(studySetDao, flashcardDao, localStudySetMapper, localFlashcardMapper)

    @Singleton
    @Provides
    fun provideStudySetMapper(localFlashcardMapper: LocalFlashcardMapper): LocalStudySetMapper =
        LocalStudySetMapperImpl(localFlashcardMapper)

    @Singleton
    @Provides
    fun provideFlashcardMapper(): LocalFlashcardMapper = LocalFlashcardMapperImpl()

    @Singleton
    @Provides
    fun provideFlashcardDb(@ApplicationContext context : Context): FlashcardDatabase {
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
    fun provideStudySetDao(flashcardDatabase: FlashcardDatabase): StudySetDao {
        return flashcardDatabase.studySetDao()
    }

    @Singleton
    @Provides
    fun provideFlashcardDao(flashcardDatabase: FlashcardDatabase): FlashcardDao {
        return flashcardDatabase.flashcardDao()
    }
}