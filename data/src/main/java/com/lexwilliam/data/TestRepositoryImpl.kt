package com.lexwilliam.data

import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.domain.TestRepository
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.Test
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): TestRepository {

    override suspend fun insertTest() {
        val test = hashMapOf(
            "first" to "Alexander",
            "last" to "William",
            "born" to 2002
        )
        firestore.collection("name")
            .add(test)
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Timber.tag("Error adding document").e(e)
            }
    }

    override suspend fun readTest() = callbackFlow {
        val snapshotListener = firestore.collection("name")
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val tests = snapshot.toObjects(Test::class.java)
                    Result.Success(tests)
                } else {
                    Result.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapshotListener.remove()
        }
    }
}