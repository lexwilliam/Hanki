package com.lexwilliam.domain

import kotlinx.coroutines.flow.Flow
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.TestResult

interface TestResultRepository {

    suspend fun insertTestResult(testResult: TestResult)

    suspend fun getTestResult(): Flow<Result<TestResult>>
}