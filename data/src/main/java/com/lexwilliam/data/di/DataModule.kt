package com.lexwilliam.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.data.PackRepositoryImpl
import com.lexwilliam.data.UserRepositoryImpl
import com.lexwilliam.data.mapper.PackMapper
import com.lexwilliam.data.mapper.PackMapperImpl
import com.lexwilliam.data.mapper.UserMapper
import com.lexwilliam.data.mapper.UserMapperImpl
import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        userMapper: UserMapper
    ): UserRepository =
        UserRepositoryImpl(auth, firestore, userMapper)

    @Singleton
    @Provides
    fun providePackRepository(
        firestore: FirebaseFirestore,
        authRepository: UserRepository,
        packMapper: PackMapper
    ): PackRepository =
        PackRepositoryImpl(firestore, authRepository, packMapper)

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

}