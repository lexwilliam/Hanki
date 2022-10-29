package com.lexwilliam.data

import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.domain.AuthRepository
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.domain.model.Result
import timber.log.Timber
import javax.inject.Inject

class PackRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) {
    suspend fun insertPack(pack: Pack) {
        authRepository.getUserProfile().collect { user ->
            when (user) {
                is Result.Success -> {
                    firestore.collection("pack").document(user.data.uid)
                        .set(pack)
                        .addOnSuccessListener {
                            Timber.d("DocumentSnapshot successfully written!")
                        }
                        .addOnFailureListener { e ->
                            Timber.tag("Error adding document").e(e)
                        }
                }
                else -> {
                    Timber.d("User Not Found")
                }
            }

        }

    }

    suspend fun insertFlashcard(documentId: String, flashcard: Flashcard) {
        firestore.collection("pack").document(documentId).collection("flashcard")
            .add(flashcard)
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Timber.tag("Error adding document").e(e)
            }
    }
}