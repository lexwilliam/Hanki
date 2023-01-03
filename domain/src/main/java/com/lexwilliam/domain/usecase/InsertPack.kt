package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.AuthRepository
import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.model.Pack
import javax.inject.Inject

interface InsertPack {
    suspend fun invoke(pack: Pack)
}

class InsertPackImpl @Inject constructor(
    private val packRepository: PackRepository
): InsertPack {
    override suspend fun invoke(pack: Pack) {
        packRepository.insertPack(pack)
    }
}