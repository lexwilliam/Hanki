package com.lexwilliam.core.di

import com.lexwilliam.core.mapper.PackMapper
import com.lexwilliam.core.mapper.PackMapperImpl
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
}