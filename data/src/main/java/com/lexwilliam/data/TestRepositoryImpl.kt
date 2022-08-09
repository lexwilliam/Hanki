package com.lexwilliam.data

import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.domain.TestRepository
import timber.log.Timber
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): TestRepository {

    override suspend fun insertTest() {
        val test = hashMapOf(
            "first" to "Alex",
            "last" to "Will",
            "born" to 2002
        )
        firestore.collection("test")
            .add(test)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Timber.tag("Error adding document").e(e)
            }
    }



}