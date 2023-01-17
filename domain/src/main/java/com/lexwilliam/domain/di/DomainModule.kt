package com.lexwilliam.domain.di

import com.lexwilliam.domain.UserRepository
import com.lexwilliam.domain.PackRepository
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
    fun provideGetUserProfile(
        authRepository: UserRepository
    ): GetUserProfile =
        GetUserProfileImpl(authRepository)

    @Singleton
    @Provides
    fun provideIsUserAuthenticated(
        authRepository: UserRepository
    ): IsUserAuthenticated =
        IsUserAuthenticatedImpl(authRepository)

    @Singleton
    @Provides
    fun provideInsertUser(
        authRepository: UserRepository
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
    fun provideGetPackCollection(
        packRepository: PackRepository
    ): GetPackCollection =
        GetPackCollectionImpl(packRepository)
}