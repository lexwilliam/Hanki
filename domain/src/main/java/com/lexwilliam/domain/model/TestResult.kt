package com.lexwilliam.domain.model

data class TestResult(
    val userId: String,
    val wordList: List<Word>
) {
    data class Word(
        val question: String,
        val answer: String,
        var correct: Int,
        var incorrect: Int,
        var packIdList: List<String>
    )
}