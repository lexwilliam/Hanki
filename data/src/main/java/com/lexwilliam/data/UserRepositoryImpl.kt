package com.lexwilliam.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.lexwilliam.data.mapper.PackMapper
import com.lexwilliam.data.mapper.UserMapper
import com.lexwilliam.data.model.UserResponse
import com.lexwilliam.domain.UserRepository
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userMapper: UserMapper
): UserRepository {

    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    override suspend fun getUserProfile(): Flow<Result<User>> = callbackFlow {
        trySend(Result.Loading)

        lateinit var subscription: ListenerRegistration

        if (isUserAuthenticatedInFirebase()) {
            auth.currentUser?.let {
                subscription = firestore.collection("user").document(it.uid)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            trySend(Result.Error("Error"))
                        } else {
                            value?.let {
                                val data = value.toObject(UserResponse::class.java)
                                data?.let { d ->
                                    trySend(Result.Success(userMapper.toDomain(d)))
                                }
                            }
                        }
                    }
            }
        }
        awaitClose {
            subscription.remove()
        }
    }

    override suspend fun insertUser() {
        if (isUserAuthenticatedInFirebase()) {
            auth.currentUser?.let { currentUser ->
                firestore.collection("user").document(currentUser.uid)
                    .set(
                        User(
                            currentUser.uid,
                            currentUser.displayName,
                            currentUser.photoUrl.toString(),
                            emptyList()
                        )
                    )
                    .addOnSuccessListener {
                        Timber.d("User ${currentUser.displayName} successfully inserted")
                    }
                    .addOnFailureListener {
                        Timber.e("Insert user failed: $it")
                    }
            }
        } else {
            Timber.e("Can't get current user")
        }
    }


}