package com.lexwilliam.domain

interface StorageRepository {

    suspend fun uploadFile(imageId: String)
}