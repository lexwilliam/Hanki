package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.PackRepository
import com.lexwilliam.domain.TestResultRepository
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.TestResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetTestResult {
    suspend fun invoke(): Flow<Result<TestResult>>
}

class GetTestResultImpl @Inject constructor(
    private val testResultRepository: TestResultRepository
): GetTestResult {
    override suspend fun invoke():Flow<Result<TestResult>> =
        testResultRepository.getTestResult()
}