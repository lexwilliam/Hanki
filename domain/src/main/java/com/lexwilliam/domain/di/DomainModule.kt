package com.lexwilliam.domain.di

import com.lexwilliam.domain.FlashcardRepository
import com.lexwilliam.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideGetAllStudySets(
        flashcardRepository: FlashcardRepository
    ): GetAllStudySets =
        GetAllStudySetsImpl(flashcardRepository)

    @Singleton
    @Provides
    fun provideGetStudySetsById(
        flashcardRepository: FlashcardRepository
    ): GetStudySetById =
        GetStudySetByIdImpl(flashcardRepository)

    @Singleton
    @Provides
    fun provideInsertStudySet(
        flashcardRepository: FlashcardRepository
    ): InsertStudySet =
        InsertStudySetImpl(flashcardRepository)

    @Singleton
    @Provides
    fun provideUpdateStudySet(
        flashcardRepository: FlashcardRepository
    ): UpdateStudySet =
        UpdateStudySetImpl(flashcardRepository)

    @Singleton
    @Provides
    fun provideDeleteStudySet(
        flashcardRepository: FlashcardRepository
    ): DeleteStudySet =
        DeleteStudySetImpl(flashcardRepository)

    @Singleton
    @Provides
    fun provideInsertFlashcard(
        flashcardRepository: FlashcardRepository
    ): InsertFlashcard =
        InsertFlashcardImpl(flashcardRepository)

    @Singleton
    @Provides
    fun provideUpdateFlashcard(
        flashcardRepository: FlashcardRepository
    ): UpdateFlashcard =
        UpdateFlashcardImpl(flashcardRepository)

    @Singleton
    @Provides
    fun provideDeleteFlashcard(
        flashcardRepository: FlashcardRepository
    ): DeleteFlashcard =
        DeleteFlashcardImpl(flashcardRepository)

}