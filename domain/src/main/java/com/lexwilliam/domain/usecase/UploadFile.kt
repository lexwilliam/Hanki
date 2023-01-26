package com.lexwilliam.domain.usecase

import android.net.Uri
import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.StorageRepository
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UploadFile {
    suspend fun invoke(imageId: String, imageUri: Uri): Flow<Result<String>>
}

class UploadFileImpl @Inject constructor(
    private val storageRepository: StorageRepository
): UploadFile {
    override suspend fun invoke(imageId: String, imageUri: Uri): Flow<Result<String>> =
        storageRepository.uploadFile(imageId, imageUri)
}