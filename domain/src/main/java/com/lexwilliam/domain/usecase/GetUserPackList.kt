package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.model.PackInfo
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUserPackList {
    suspend fun invoke(userId: String): Flow<Result<List<PackInfo>>>
}

class GetUserPackListImpl @Inject constructor(
    private val packRepository: PackRepository
): GetUserPackList {
    override suspend fun invoke(userId: String): Flow<Result<List<PackInfo>>> =
        packRepository.getUserPackList(userId)
}