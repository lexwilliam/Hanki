package com.lexwilliam.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.domain.AuthRepository
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthRepository {
    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    override suspend fun getUserProfile(): Flow<Result<User>> = flow {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            try {
                emit(Result.Loading)
                val user = User(
                    uid = currentUser.uid,
                    name = currentUser.displayName,
                    photoUrl = currentUser.photoUrl
                )
                emit(Result.Success(user))
            } catch (e: Exception) {
                emit(Result.Error(e.toString()))
            }
        }

    }

    override suspend fun insertUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            firestore.collection("user").document(currentUser.uid)
                .set(User(currentUser.uid, currentUser.displayName, currentUser.photoUrl))
        }
    }


}