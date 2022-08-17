package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.AuthRepository
import javax.inject.Inject

interface IsUserAuthenticated {
    fun invoke(): Boolean
}

class IsUserAuthenticatedImpl @Inject constructor(
    private val authRepository: AuthRepository
): IsUserAuthenticated {
    override fun invoke(): Boolean =
        authRepository.isUserAuthenticatedInFirebase()
}