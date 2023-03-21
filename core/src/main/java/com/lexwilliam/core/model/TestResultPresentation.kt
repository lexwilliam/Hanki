package com.lexwilliam.core.model

data class TestResultPresentation(
    val userId: String,
    val wordList: MutableList<Word>
) {
    data class Word(
        val question: String,
        val answer: String,
        var correct: Int,
        var incorrect: Int,
        var packIdList: MutableList<String>
    )
}