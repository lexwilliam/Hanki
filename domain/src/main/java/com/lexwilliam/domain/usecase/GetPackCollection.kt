package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPackCollection {
    suspend fun invoke(): Flow<Result<List<Pack>>>
}

class GetPackCollectionImpl @Inject constructor(
    private val packRepository: PackRepository
): GetPackCollection {
    override suspend fun invoke(): Flow<Result<List<Pack>>> =
        packRepository.getPackCollection()
}