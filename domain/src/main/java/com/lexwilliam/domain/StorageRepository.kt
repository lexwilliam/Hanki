package com.lexwilliam.domain

import android.net.Uri
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface StorageRepository {

    suspend fun uploadFile(imageId: String, imageUri: Uri): Flow<Result<String>>

}