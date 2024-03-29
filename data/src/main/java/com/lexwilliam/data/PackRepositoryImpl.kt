package com.lexwilliam.data

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.lexwilliam.data.mapper.PackMapper
import com.lexwilliam.data.model.PackResponse
import com.lexwilliam.domain.UserRepository
import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.domain.model.PackInfo
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

class PackRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository,
    private val packMapper: PackMapper
): PackRepository {

    override suspend fun insertPack(pack: Pack) {
        pack.id?.let {
            firestore.collection("packs").document(it)
                .set(pack)
                .addOnSuccessListener {
                    Timber.d("DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Timber.tag("Error adding document").e(e)
                }
        }

        userRepository.getUserProfile().collect { user ->
            when (user) {
                is Result.Success -> {
                    val packInfo = PackInfo(
                        pack.id,
                        pack.title,
                        pack.pictureUrl,
                        pack.creatorName,
                        pack.creatorPhotoUrl.toString()
                    )
                    val docRef = firestore.collection("user").document(user.data.uid)
                    docRef.update("packs", FieldValue.arrayUnion(packInfo))
                        .addOnCompleteListener {
                            Timber.d("Update Successfully")
                        }
                        .addOnFailureListener {
                            Timber.d("Update Unsuccessful")
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

    override suspend fun getPack(id: String): Flow<Result<Pack>> = callbackFlow {
        trySend(Result.Loading)

        val subscription = firestore.collection("packs").document(id)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Result.Error("Error"))
                } else {
                    value?.let {
                        val data = value.toObject(PackResponse::class.java)
                        data?.let { d ->
                            trySend(Result.Success(packMapper.toDomain(d)))
                        }
                    }
                }
            }

        awaitClose {
            subscription.remove()
        }
    }

    override suspend fun getPackCollection(): Flow<Result<List<Pack>>> = callbackFlow {
        trySend(Result.Loading)

        val subscription = firestore.collection("packs")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    error.message?.let {
                        trySend(Result.Error(it))
                    }
                } else {
                    value?.let {
                        val data = value.toObjects(PackResponse::class.java)
                        trySend(Result.Success(data.map { packMapper.toDomain(it) }))
                    }
                }
            }
        awaitClose {
            subscription.remove()
        }
    }
}