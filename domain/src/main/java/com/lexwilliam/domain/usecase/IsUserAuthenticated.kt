package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.UserRepository
import javax.inject.Inject

interface IsUserAuthenticated {
    fun invoke(): Boolean
}

class IsUserAuthenticatedImpl @Inject constructor(
    private val authRepository: UserRepository
): IsUserAuthenticated {
    override fun invoke(): Boolean =
        authRepository.isUserAuthenticatedInFirebase()
}