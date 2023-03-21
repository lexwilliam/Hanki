package com.lexwilliam.core.di

import com.lexwilliam.core.mapper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureModule {

    @Singleton
    @Provides
    fun providePackMapper(): PackMapper =
        PackMapperImpl()

    @Singleton
    @Provides
    fun provideUserMapper(
        packMapper: PackMapper
    ): UserMapper =
        UserMapperImpl(packMapper)

    @Singleton
    @Provides
    fun provideTestResultMapper(): TestResultMapper =
        TestResultMapperImpl()
}