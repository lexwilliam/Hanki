package com.lexwilliam.data.di

import com.lexwilliam.data.FlashcardLocalSource
import com.lexwilliam.data.mapper.RepositoryFlashcardMapper
import com.lexwilliam.data.mapper.RepositoryFlashcardMapperImpl
import com.lexwilliam.data.mapper.RepositoryStudySetMapper
import com.lexwilliam.data.mapper.RepositoryStudySetMapperImpl
import com.lexwilliam.data.repository.FlashcardRepositoryImpl
import com.lexwilliam.domain.FlashcardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlashcardRepositoryModule {

    @Singleton
    @Provides
    fun provideFlashcardRepository(
        flashcardLocalSource: FlashcardLocalSource,
        repositoryStudySetMapper: RepositoryStudySetMapper,
        repositoryFlashcardMapper: RepositoryFlashcardMapper
    ): FlashcardRepository =
        FlashcardRepositoryImpl(flashcardLocalSource, repositoryStudySetMapper, repositoryFlashcardMapper)

    @Singleton
    @Provides
    fun provideStudySetMapper(repositoryFlashcardMapper: RepositoryFlashcardMapper): RepositoryStudySetMapper =
        RepositoryStudySetMapperImpl(repositoryFlashcardMapper)

    @Singleton
    @Provides
    fun provideFlashcardMapper(): RepositoryFlashcardMapper =
        RepositoryFlashcardMapperImpl()
}