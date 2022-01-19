package com.lexwilliam.hanki.util

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

object TimeProvider {
    fun getCurrentDate(): String {
        val dateFormat = DateTimeFormat
            .forPattern("dd MMM")

        return dateFormat.print(LocalDateTime())
    }
}