package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.UserRepository
import javax.inject.Inject

interface InsertUser {
    suspend fun invoke()
}

class InsertUserImpl @Inject constructor(
    private val authRepository: UserRepository
): InsertUser {
    override suspend fun invoke() {
        authRepository.insertUser()
    }
}