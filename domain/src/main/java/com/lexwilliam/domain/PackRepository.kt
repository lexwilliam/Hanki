package com.lexwilliam.domain

import com.lexwilliam.domain.model.Pack
import kotlinx.coroutines.flow.Flow
import com.lexwilliam.domain.model.Result

interface PackRepository {
    suspend fun insertPack(pack: Pack)

    suspend fun getPack(id: String): Flow<Result<Pack>>

    suspend fun getUserPackList(userId: Pack): Flow<Result<List<Pack>>>
}