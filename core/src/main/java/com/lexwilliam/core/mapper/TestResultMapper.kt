package com.lexwilliam.core.mapper

import com.lexwilliam.core.model.TestResultPresentation
import com.lexwilliam.domain.model.TestResult
import javax.inject.Inject

interface TestResultMapper {

    fun toPresentation(testResult: TestResult): TestResultPresentation

    fun toDomain(testResult: TestResultPresentation): TestResult
}

class TestResultMapperImpl @Inject constructor(): TestResultMapper {
    override fun toPresentation(testResult: TestResult): TestResultPresentation =
        TestResultPresentation(testResult.userId, testResult.wordList.map { toPresentation(it) }.toMutableList())

    private fun toPresentation(word: TestResult.Word): TestResultPresentation.Word =
        TestResultPresentation.Word(word.question, word.answer, word.correct, word.incorrect, word.packIdList.toMutableList())

    override fun toDomain(testResult: TestResultPresentation): TestResult =
        TestResult(testResult.userId, testResult.wordList.map { toDomain(it) })

    private fun toDomain(word: TestResultPresentation.Word): TestResult.Word =
        TestResult.Word(word.question, word.answer, word.correct, word.incorrect, word.packIdList)

}