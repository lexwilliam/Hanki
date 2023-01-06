package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.UserRepository
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUserProfile {
    suspend fun invoke(): Flow<Result<User>>
}

class GetUserProfileImpl @Inject constructor(
    private val authRepository: UserRepository
): GetUserProfile {
    override suspend fun invoke(): Flow<Result<User>> =
        authRepository.getUserProfile()
}