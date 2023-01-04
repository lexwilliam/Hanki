package com.lexwilliam.domain.di

import com.lexwilliam.domain.AuthRepository
import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.TestRepository
import com.lexwilliam.domain.usecase.*
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

    @Singleton
    @Provides
    fun provideReadTest(
        testRepository: TestRepository
    ): ReadTest =
        ReadTestImpl(testRepository)

    @Singleton
    @Provides
    fun provideGetUserProfile(
        authRepository: AuthRepository
    ): GetUserProfile =
        GetUserProfileImpl(authRepository)

    @Singleton
    @Provides
    fun provideIsUserAuthenticated(
        authRepository: AuthRepository
    ): IsUserAuthenticated =
        IsUserAuthenticatedImpl(authRepository)

    @Singleton
    @Provides
    fun provideInsertUser(
        authRepository: AuthRepository
    ): InsertUser =
        InsertUserImpl(authRepository)

    @Singleton
    @Provides
    fun provideInsertPack(
        packRepository: PackRepository
    ): InsertPack =
        InsertPackImpl(packRepository)

    @Singleton
    @Provides
    fun provideGetPack(
        packRepository: PackRepository
    ): GetPack =
        GetPackImpl(packRepository)

    @Singleton
    @Provides
    fun provideGetUserPackList(
        packRepository: PackRepository
    ): GetUserPackList =
        GetUserPackListImpl(packRepository)
}