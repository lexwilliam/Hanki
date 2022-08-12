package com.lexwilliam.domain

import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.Test
import kotlinx.coroutines.flow.Flow

interface TestRepository {

    suspend fun insertTest()

    fun readTest(): Flow<Result<List<Test>>>
}