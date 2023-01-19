package com.lexwilliam.data

import com.google.firebase.storage.FirebaseStorage
import com.lexwilliam.domain.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
): StorageRepository {

    override suspend fun uploadFile(imageId: String) {
        val storageRef = storage.reference
        val imageRef = storageRef.child(imageId)
    }

}