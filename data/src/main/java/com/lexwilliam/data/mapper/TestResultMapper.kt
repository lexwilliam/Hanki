package com.lexwilliam.data.mapper

import com.lexwilliam.data.model.TestResultResponse
import com.lexwilliam.domain.model.TestResult
import javax.inject.Inject

interface TestResultMapper {

    fun toResponse(testResult: TestResult): TestResultResponse

    fun toDomain(testResult: TestResultResponse): TestResult
}

class TestResultMapperImpl @Inject constructor(): TestResultMapper {
    override fun toResponse(testResult: TestResult): TestResultResponse =
        TestResultResponse(testResult.userId, testResult.wordList.map { toResponse(it) })

    private fun toResponse(word: TestResult.Word): TestResultResponse.Word =
        TestResultResponse.Word(word.question, word.answer, word.correct, word.incorrect, word.packIdList)

    override fun toDomain(testResult: TestResultResponse): TestResult =
        TestResult(testResult.userId?:"", testResult.wordList?.map { toDomain(it) } ?: emptyList())

    private fun toDomain(word: TestResultResponse.Word): TestResult.Word =
        TestResult.Word(word.question?:"", word.answer?:"", word.correct?:-1, word.incorrect?:-1, word.packIdList?: emptyList())

}