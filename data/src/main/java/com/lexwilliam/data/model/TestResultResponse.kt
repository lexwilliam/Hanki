package com.lexwilliam.data.model

data class TestResultResponse(
    val userId: String? = null,
    val wordList: List<Word>? = null
) {
    data class Word(
        val question: String? = null,
        val answer: String? = null,
        var correct: Int? = null,
        var incorrect: Int? = null,
        var packIdList: List<String>? = null
    )
}