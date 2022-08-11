package com.lexwilliam.domain

import kotlinx.coroutines.flow.Flow

interface TestRepository {

    suspend fun insertTest()

    suspend fun readTest()
}