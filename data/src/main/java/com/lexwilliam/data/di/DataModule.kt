package com.lexwilliam.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.lexwilliam.data.PackRepositoryImpl
import com.lexwilliam.data.StorageRepositoryImpl
import com.lexwilliam.data.TestResultRepositoryImpl
import com.lexwilliam.data.UserRepositoryImpl
import com.lexwilliam.data.mapper.*
import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.StorageRepository
import com.lexwilliam.domain.TestResultRepository
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
    fun provideStorageRepository(
        storage: FirebaseStorage
    ): StorageRepository =
        StorageRepositoryImpl(storage)

    @Singleton
    @Provides
    fun provideTestResultRepository(
        firestore: FirebaseFirestore,
        userRepository: UserRepository,
        testResultMapper: TestResultMapper
    ): TestResultRepository =
        TestResultRepositoryImpl(firestore, userRepository, testResultMapper)

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