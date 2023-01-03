package com.lexwilliam.domain

import com.lexwilliam.domain.model.Pack

interface PackRepository {
    suspend fun insertPack(pack: Pack)
}