package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPack {
    suspend fun invoke(id: String): Flow<Result<Pack>>
}

class GetPackImpl @Inject constructor(
    private val packRepository: PackRepository
): GetPack {
    override suspend fun invoke(id: String): Flow<Result<Pack>> =
        packRepository.getPack(id)
}