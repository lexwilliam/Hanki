package com.lexwilliam.data

import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.domain.AuthRepository
import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.domain.model.Result
import timber.log.Timber
import javax.inject.Inject

class PackRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
): PackRepository {
    override suspend fun insertPack(pack: Pack) {
        authRepository.getUserProfile().collect { user ->
            when (user) {
                is Result.Success -> {
                    firestore.collection("packs").document(pack.id)
                        .set(pack)
                        .addOnSuccessListener {
                            Timber.d("DocumentSnapshot successfully written!")
                        }
                        .addOnFailureListener { e ->
                            Timber.tag("Error adding document").e(e)
                        }
                }
                is Result.Loading -> {
                    Timber.d("Loading")
                }
                is Result.Error -> {
                    Timber.d("Error")
                }
            }
        }
    }
}