package com.lexwilliam.domain.usecase

import com.lexwilliam.domain.TestRepository
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.Test
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ReadTest {
    fun invoke(): Flow<Result<List<Test>>>
}

class ReadTestImpl @Inject constructor(
    private val testRepository: TestRepository
): ReadTest {
    override fun invoke(): Flow<Result<List<Test>>> =
        testRepository.readTest()

}