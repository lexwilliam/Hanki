package com.lexwilliam.data

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.lexwilliam.domain.StorageRepository
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.flow

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
): StorageRepository {

    override suspend fun uploadFile(imageId: String, imageUri: Uri) = callbackFlow {
        trySend(Result.Loading)

        val storageRef = storage.reference
        val ref = storageRef.child("image/$imageId")
        val uploadTask = ref.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Timber.d("Success: %s", downloadUri.toString())
                trySend(Result.Success(downloadUri.toString()))
            } else {
                Timber.d("Error")
                trySend(Result.Error(task.exception.toString()))
            }
        }

        awaitClose {
            uploadTask.cancel()
        }
    }

}