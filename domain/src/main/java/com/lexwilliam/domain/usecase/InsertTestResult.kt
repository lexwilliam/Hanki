package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.TestResultRepository
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.TestResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface InsertTestResult {
    suspend fun invoke(testResult: TestResult)
}

class InsertTestResultImpl @Inject constructor(
    private val testResultRepository: TestResultRepository
): InsertTestResult {
    override suspend fun invoke(testResult: TestResult) =
        testResultRepository.insertTestResult(testResult)
}