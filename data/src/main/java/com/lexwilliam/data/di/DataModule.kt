package com.lexwilliam.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.data.AuthRepositoryImpl
import com.lexwilliam.data.PackRepositoryImpl
import com.lexwilliam.data.TestRepositoryImpl
import com.lexwilliam.domain.AuthRepository
import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.TestRepository
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
    fun provideTestRepository(
        firestore: FirebaseFirestore
    ): TestRepository =
        TestRepositoryImpl(firestore)

    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository =
        AuthRepositoryImpl(auth, firestore)

    @Singleton
    @Provides
    fun providePackRepository(
        firestore: FirebaseFirestore,
        authRepository: AuthRepository
    ): PackRepository =
        PackRepositoryImpl(firestore, authRepository)


}