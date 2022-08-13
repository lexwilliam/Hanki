package com.lexwilliam.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lexwilliam.data.TestRepositoryImpl
import com.lexwilliam.domain.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideTestRepository(
        firestore: FirebaseFirestore
    ): TestRepository =
        TestRepositoryImpl(firestore)
}