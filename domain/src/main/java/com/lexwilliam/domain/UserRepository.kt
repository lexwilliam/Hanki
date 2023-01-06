package com.lexwilliam.domain

import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun getUserProfile(): Flow<Result<User>>

    suspend fun insertUser()
}