package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.TestRepository
import javax.inject.Inject

interface InsertTest {
    suspend fun invoke()
}

class InsertTestImpl @Inject constructor(
    private val testRepository: TestRepository
): InsertTest {
    override suspend fun invoke() =
        testRepository.insertTest()

}