package com.lexwilliam.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.lexwilliam.domain.AuthRepository
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {
    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    override suspend fun getUserProfile(): Flow<Result<User>> = flow {
        try {
            emit(Result.Loading)
            val user = User(
                name = auth.currentUser?.displayName,
                photoUrl = auth.currentUser?.photoUrl
            )
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

}