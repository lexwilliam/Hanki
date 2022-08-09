package com.lexwilliam.domain.di

import com.lexwilliam.domain.TestRepository
import com.lexwilliam.domain.usecase.InsertTest
import com.lexwilliam.domain.usecase.InsertTestImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideInsertTest(
        testRepository: TestRepository
    ): InsertTest =
        InsertTestImpl(testRepository)
}