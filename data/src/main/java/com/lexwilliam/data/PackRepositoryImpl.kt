package com.lexwilliam.data

import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.domain.AuthRepository
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.Pack
import timber.log.Timber
import javax.inject.Inject

class PackRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) {
    suspend fun insertPack(pack: Pack) {
        firestore.collection("pack")
            .add(pack)
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Timber.tag("Error adding document").e(e)
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