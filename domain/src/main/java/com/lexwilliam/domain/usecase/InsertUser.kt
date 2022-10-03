package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.AuthRepository
import javax.inject.Inject

interface InsertUser {
    suspend fun invoke()
}

class InsertUserImpl @Inject constructor(
    private val authRepository: AuthRepository
): InsertUser {
    override suspend fun invoke() {
        authRepository.insertUser()
    }
}