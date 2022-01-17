package com.lexwilliam.hanki.presentation.viewmodel.di

import com.lexwilliam.hanki.presentation.viewmodel.mapper.FlashcardMapperPresentation
import com.lexwilliam.hanki.presentation.viewmodel.mapper.FlashcardMapperPresentationImpl
import com.lexwilliam.hanki.presentation.viewmodel.mapper.StudySetMapperPresentation
import com.lexwilliam.hanki.presentation.viewmodel.mapper.StudySetMapperPresentationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Singleton
    @Provides
    fun provideStudySetMapper(flashcardMapperPresentation: FlashcardMapperPresentation): StudySetMapperPresentation
        = StudySetMapperPresentationImpl(flashcardMapperPresentation)

    @Singleton
    @Provides
    fun provideFlashcardMapper(): FlashcardMapperPresentation
        = FlashcardMapperPresentationImpl()

}